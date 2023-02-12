package lightning.gathergo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import lightning.gathergo.model.Subscription;
import lightning.gathergo.repository.NotificationSubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FcmMessagingService {
    private final Logger logger = LoggerFactory.getLogger(FcmMessagingService.class);

    private final NotificationSubscriptionRepository notificationSubscriptionRepository;

    private final static String notificationTitle = "알림이 도착했습니다";

    @Value("${firebase.credential.path}")
    private String credentialPath;  // credential
    private final ObjectMapper objectMapper;

    Map<Integer, List<String>> registrationTokens = new ConcurrentHashMap<>();  // 여러 스레드의 동일 토픽 접근 병행성 제어

    public FcmMessagingService(NotificationSubscriptionRepository notificationSubscriptionRepository, ObjectMapper objectMapper) {
        this.notificationSubscriptionRepository = notificationSubscriptionRepository;
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
        List<Subscription> subscriptions = notificationSubscriptionRepository.findAll();

        subscriptions.forEach(subscription -> {
            int articleId = subscription.getArticleId();
            String deviceToken = subscription.getDeviceToken();

            registrationTokens.computeIfAbsent(articleId, k -> new ArrayList<>()).add(deviceToken);
        });
    }

    public void subscribeToTopic(String topic, String token) {
        TopicManagementResponse response = null;

        // Subscribe the devices corresponding to the registration tokens to the
        // topic.
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(
                    registrationTokens, topic);
        } catch (FirebaseMessagingException e) {
            logger.error(e.getMessage());
        }

        // See the TopicManagementResponse reference documentation
        // for the contents of response.
        logger.info(response.getSuccessCount() + " tokens were subscribed successfully");
    }

    public String sendMessageToTopic(String topic, String title, String body) {  // TODO: Article의 멤버들에게 알림 발송
        // The topic name can be optionally prefixed with "/topics/".

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .putData("score", "850")
                .putData("time", "2:45")
                .setTopic(topic)
                .build();  // .setCondition 추가하면 한 번에 여러 topic에도 전달 가능
        String response = "";

        try {
            // Send a message to the devices subscribed to the provided topic.
           response = FirebaseMessaging.getInstance().send(message);

            // Response is a message ID string.
            System.out.println("Successfully sent message: " + response);
        } catch (FirebaseMessagingException e) {
            logger.error(e.getMessage());
        }

        return response;
    }

    public String sendMessageToToken(String token, String title, String body) {  // TODO: Article의 멤버들에게 알림 발송

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .setToken(token)
                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
                        .setNotification(new WebpushNotification(title,
                                body))
                        .build())
                .build();  // .setCondition 추가하면 한 번에 여러 topic에도 전달 가능
        String response = "";

        try {
            // Send a message to the devices subscribed to the provided topic.
            response = FirebaseMessaging.getInstance().send(message);

            // Response is a message ID string.
            System.out.println("Successfully sent message: " + response);
        } catch (FirebaseMessagingException e) {
            logger.error(e.getMessage());
        }

        return response;
    }
}
