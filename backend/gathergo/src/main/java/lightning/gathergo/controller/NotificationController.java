package lightning.gathergo.controller;

import lightning.gathergo.dto.CommonResponseDTO;
import lightning.gathergo.dto.SubscriptionDto;
import lightning.gathergo.service.FcmMessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CommonResponseDTO<?>> subscribe(@RequestBody SubscriptionDto.Request request) {
        CommonResponseDTO<Object> responseDto;

        String deviceToken = request.getDeviceToken();
        String articleId = request.getArticleId();

        logger.info("topic 구독 {}, {}", articleId, deviceToken);

        boolean subscribed = messagingService.subscribeToTopic(articleId, deviceToken);

        if(subscribed)
            responseDto = new CommonResponseDTO<>(1, articleId + " 구독 성공", null);
        else
            responseDto = new CommonResponseDTO<>(0, articleId + " 구독 실패", null);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponseDTO<?>> unsubscribe(@RequestBody SubscriptionDto.Request request) {
        CommonResponseDTO<Object> responseDto;

        String deviceToken = request.getDeviceToken();
        String articleId = request.getArticleId();

        logger.info("topic 구독 해제 {}, {}", articleId, deviceToken);

        boolean unSubscribed = messagingService.unsubscribeFromTopic(articleId, deviceToken);

        if(unSubscribed)
            responseDto = new CommonResponseDTO<>(1, articleId + " 구독 해제 성공", null);
        else
            responseDto = new CommonResponseDTO<>(0, articleId + " 구독 해제 실패", null);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
