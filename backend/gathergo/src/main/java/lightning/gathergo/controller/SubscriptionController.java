package lightning.gathergo.controller;

import lightning.gathergo.dto.CommonResponseDTO;
import lightning.gathergo.dto.SubscriptionDto;
import lightning.gathergo.service.FcmMessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/subscription", produces = APPLICATION_JSON_VALUE)
public class SubscriptionController {
    // FCM 메시지 구독 컨트롤러
    private final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);
    private final FcmMessagingService messagingService;

    public SubscriptionController(FcmMessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @PostMapping
    public ResponseEntity<CommonResponseDTO<?>> subscribe(@RequestBody SubscriptionDto.Request request) {
        return handleSubscriptionRequest(request, true);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponseDTO<?>> unsubscribe(@RequestBody SubscriptionDto.Request request) {
        return handleSubscriptionRequest(request, false);
    }

    private ResponseEntity<CommonResponseDTO<?>> handleSubscriptionRequest(SubscriptionDto.Request request, boolean isSubscribe) {
        CommonResponseDTO<Object> responseDto;
        boolean success;

        String deviceToken = request.getDeviceToken();
        String articleId = request.getArticleId();

        // validation
        if(deviceToken == null || deviceToken.isBlank() || articleId == null || articleId.isBlank()) {
            return new ResponseEntity<>(new CommonResponseDTO<>(0, articleId + (isSubscribe ? " 구독 실패" : " 구독 해제 실패"), null), HttpStatus.BAD_REQUEST);
        }

        logger.info("topic {} {}, {}", articleId, (isSubscribe ? "구독" : "구독 해제"), deviceToken);

        String action;

        if (isSubscribe) {
            success = messagingService.subscribeToTopic(articleId, deviceToken);
            action = "구독";
        } else {
            success = messagingService.unsubscribeFromTopic(articleId, deviceToken);
            action = "구독 해제";
        }

        String message = articleId + (success ? " " + action + " 성공" : " " + action + " 실패");
        responseDto = new CommonResponseDTO<>(success ? 1 : 0, message, null);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
