package lightning.gathergo.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import lightning.gathergo.repository.SubscriptionRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ActiveProfiles("local")
@Transactional
// @ExtendWith(MockitoExtension.class)
@SpringBootTest
public class FcmMessagingServiceTest {
    private final Logger logger = LoggerFactory.getLogger(FcmMessagingServiceTest.class);
    private final FcmMessagingService messagingService;

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public FcmMessagingServiceTest(FcmMessagingService messagingService, SubscriptionRepository subscriptionRepository) {
        this.messagingService = messagingService;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Test
    @DisplayName("빈 구독 리스트에 구독 시도 테스트")
    public void testSubscribeToNonExistingTopic() {
        SoftAssertions softly = new SoftAssertions();

        // given
        int topic = 1;
        String deviceToken = "device_token";

        // when
        boolean result = messagingService.subscribeToTopic(topic, deviceToken);

        cleanUp(List.of(deviceToken), topic);

        softly.assertThat(result).isTrue();
        softly.assertThat(subscriptionRepository.findByArticleId(topic)).isNotNull().hasSize(1);
        softly.assertAll();
    }

    @Test
    @DisplayName("존재하는 구독 리스트에 구독 추가 시도")
    public void testSubscribeToExistingTopic() {
        SoftAssertions softly = new SoftAssertions();

        // given
        int topic = 1;
        String deviceToken1 = "device_token1";
        String deviceToken2 = "device_token2";

        // when
        boolean result = messagingService.subscribeToTopic(topic, deviceToken1);
        result = messagingService.subscribeToTopic(topic, deviceToken2);

        cleanUp(List.of(deviceToken1, deviceToken2), topic);

        softly.assertThat(result).isTrue();
        softly.assertThat(subscriptionRepository.findByArticleId(topic)).isNotNull().hasSize(2);
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
