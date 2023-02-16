package lightning.gathergo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import lightning.gathergo.model.Subscription;
import lightning.gathergo.repository.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FcmMessagingService {
    private final Logger logger = LoggerFactory.getLogger(FcmMessagingService.class);

    private final SubscriptionRepository subscriptionRepository;

    private final static String notificationTitle = "알림이 도착했습니다";

    @Value("${firebase.credential.path}")
    private String credentialPath;  // credential
    private final ObjectMapper objectMapper;

    private final Map<Integer, Set<String>> registrationTokens = new ConcurrentHashMap<>();  // 여러 스레드의 동일 토픽 접근 병행성 제어
    private final Map<Integer, List<String>> pendingUpdates = new ConcurrentHashMap<>();


    public FcmMessagingService(SubscriptionRepository subscriptionRepository, ObjectMapper objectMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    private void init() {
        // 1. FCM 연결
        try{
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(credentialPath).getInputStream())).build();

            if(FirebaseApp.getApps().isEmpty()){
                FirebaseApp.initializeApp(options);
            }
            logger.info("FirebaseApp initialization complete");
        } catch (IOException e){
            e.printStackTrace();
        }

        // 2. 서버 재시동 후 DB 참조해 각 게시글 별 구독 정보 불러오기
        List<Subscription> subscriptions = subscriptionRepository.findAll();

        subscriptions.forEach(subscription -> {
            int articleId = subscription.getArticleId();
            String deviceToken = subscription.getDeviceToken();

            registrationTokens.computeIfAbsent(articleId, k -> new HashSet<>()).add(deviceToken);
        });
    }

    /**
     * 알림 수신 위한 토픽 구독
     * @param topic 구독 토픽(게시글 id)
     * @param deviceToken 기기 식별토큰
     * @return 성공 여부
     */
    public boolean subscribeToTopic(int topic, String deviceToken) {
        registrationTokens.computeIfAbsent(topic, k -> new HashSet<>()).add(deviceToken);

        TopicManagementResponse response = null;

        registrationTokens.computeIfPresent(topic, (k, v) -> {
            v.add(deviceToken);
            return v;
        });

        List<String> tokensToRegister = new ArrayList<>(registrationTokens.get(topic));

        // 매 번 레코드가 추가될 때마다 호출하면 오버헤드 증가
        try {
            // 1. FCM에 추가
            response = FirebaseMessaging.getInstance()
                    .subscribeToTopic(tokensToRegister, String.valueOf(topic));
            // 2. DB에 구독 정보 추가
            subscriptionRepository.save(topic, deviceToken);

            logger.info("{} tokens were subscribed successfully", response.getSuccessCount());
        } catch (FirebaseMessagingException e) {
            logger.error("could not add token to given topic: {}, {}", topic, e.getMessage());
            return false;
        } catch (DataIntegrityViolationException e) {  // 이미 등록된 토큰
            logger.error("토큰 중복: {}, {}", topic, e.getMessage());
        }

        return true;
    }

    /**
     * 알림 수신 위한 토픽 구독 해제
     * @param topic 구독 토픽(게시글 id)
     * @param deviceToken 기기 식별토큰
     * @return 성공 여부
     */
    public boolean unsubscribeFromTopic(int topic, String deviceToken) {
        TopicManagementResponse response = null;
        int affectedRows;  // DB에서 삭제된 구독 정보의 수, 정상 동작은 1을 반환

        Set<String> tokens = registrationTokens.computeIfPresent(topic, (t, existingTokens) -> {  // atomic operation
            if (existingTokens.contains(deviceToken)) {
                existingTokens.remove(deviceToken);
                return existingTokens;
            } else {
                logger.info("device token not found in topic: {}, {}", topic, deviceToken);
                return existingTokens;
            }
        });

        try {
            // 1. FCM에 추가
            response = FirebaseMessaging.getInstance()
                    .unsubscribeFromTopic(List.of(deviceToken), String.valueOf(topic));
            // 2. DB에 구독 정보 제거
            affectedRows = subscriptionRepository.deleteByArticleIdAndToken(topic, deviceToken);

            logger.info("{} tokens were unsubscribed successfully", response.getSuccessCount());
        } catch (FirebaseMessagingException e) {
            logger.error("could not add token to given topic: {}, {}", topic, e.getMessage());
            return false;
        }
        return affectedRows != 0;
    }

    public String sendMessageToTopic(String topic, Map<String, String> datas) {  // TODO: Article의 멤버들에게 알림 발송
        Message message = Message.builder()
                .putAllData(datas)
                .setTopic(topic)
                .build();  // .setCondition 추가하면 한 번에 여러 topic에도 전달 가능

        return send(message);
    }

    public String sendMessageToToken(String token, String body) {
        Message message = Message.builder()
                .setToken(token)
                .setWebpushConfig(WebpushConfig.builder()
                        .putHeader("ttl", "300")
                        .setNotification(new WebpushNotification(notificationTitle, body))
                        .build())
                .build();

        return send(message);
    }

    private String send(Message message) {
        String response = "";

        try {
            // Send a message to the devices subscribed to the provided topic.
            response = FirebaseMessaging.getInstance().send(message);

            logger.info("Successfully sent message: {}", response);
        } catch (FirebaseMessagingException e) {
            logger.error(e.getMessage());
            return "";
        }
        return response;
    }
}
