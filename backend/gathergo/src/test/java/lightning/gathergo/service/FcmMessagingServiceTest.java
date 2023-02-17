package lightning.gathergo.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import lightning.gathergo.model.Subscription;
import lightning.gathergo.repository.SubscriptionRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.mockito.Mockito.when;

@ActiveProfiles("local")
@Transactional
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class FcmMessagingServiceTest {
    private final Logger logger = LoggerFactory.getLogger(FcmMessagingServiceTest.class);
    private final FcmMessagingService messagingService;

    @Mock
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public FcmMessagingServiceTest(FcmMessagingService messagingService, SubscriptionRepository subscriptionRepository) {
        this.messagingService = messagingService;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Test
    @DisplayName("빈 구독 리스트에 구독 시도 테스트")
    public void subscribeToNonExistingTopic() {
        SoftAssertions softly = new SoftAssertions();

        // given
        String topic = "a";
        final String deviceToken = String.valueOf(UUID.randomUUID());

        // when
        boolean result = messagingService.subscribeToTopic(topic, deviceToken);

        when(subscriptionRepository.findByArticleId(topic)).thenReturn(List.of(new Subscription(topic, deviceToken)));
        // cleanUp(List.of(deviceToken), topic);

        softly.assertThat(result).isTrue();
        softly.assertThat(subscriptionRepository.findByArticleId(topic)).isNotNull().hasSize(1);
        softly.assertAll();
    }

    @Test
    @DisplayName("존재하는 구독 리스트에 구독 추가 시도")
    public void subscribeToExistingTopic() {
        SoftAssertions softly = new SoftAssertions();

        // given
        String topic = "a";
        String deviceToken1 = "device_token1";
        String deviceToken2 = "device_token2";

        // when
        boolean result = messagingService.subscribeToTopic(topic, deviceToken1);
        result = messagingService.subscribeToTopic(topic, deviceToken2);

        when(subscriptionRepository.findByArticleId(topic)).thenReturn(List.of(new Subscription(topic, deviceToken1), new Subscription(topic, deviceToken2)));

        // cleanUp(List.of(deviceToken1, deviceToken2), topic);

        softly.assertThat(result).isTrue();
        softly.assertThat(subscriptionRepository.findByArticleId(topic)).isNotNull().hasSize(2);
        softly.assertAll();

    }

    @Test
    @DisplayName("구독에 푸시 알림 보내기")
    public void sendPushNoticeToTopic() {
        SoftAssertions softly = new SoftAssertions();

        // given
        String topic = "a";
        final String deviceToken = String.valueOf(UUID.randomUUID());

        // when
        boolean result = messagingService.subscribeToTopic(topic, deviceToken);

        when(subscriptionRepository.findByArticleId(topic)).thenReturn(List.of(new Subscription(topic, deviceToken)));
        // cleanUp(List.of(deviceToken), topic);

        softly.assertThat(result).isTrue();
        softly.assertThat(subscriptionRepository.findByArticleId(topic)).isNotNull().hasSize(1);
        softly.assertAll();
    }

    private void cleanUp(List<String> tokens, int topic) {

        try {
            FirebaseMessaging.getInstance()
                    .unsubscribeFromTopic(tokens, String.valueOf(topic));
        } catch (FirebaseMessagingException e) {
            logger.error("could not add token to given topic: {}, {}", topic, e.getMessage());
        }
    }
}
