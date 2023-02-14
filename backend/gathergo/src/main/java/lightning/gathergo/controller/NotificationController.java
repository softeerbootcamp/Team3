package lightning.gathergo.controller;

import lightning.gathergo.service.FcmMessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/subscription", produces = APPLICATION_JSON_VALUE)
public class NotificationController {
    // FCM 메시지 구독 컨트롤러
    private final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    private final FcmMessagingService messagingService;

    public NotificationController(FcmMessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @PostMapping
    public void setSubscription(@RequestBody HashMap<String, String> param) {
        // TODO: DTO로 변경하기
        String deviceToken = param.get("deviceToken");
        int articleId = Integer.parseInt(param.get("articleId"));

        logger.info("topic 구독 {}, {}", articleId, deviceToken);

        boolean result = messagingService.subscribeToTopic(articleId, deviceToken);
    }
}
