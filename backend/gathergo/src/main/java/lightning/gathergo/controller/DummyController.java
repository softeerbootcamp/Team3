package lightning.gathergo.controller;

import lightning.gathergo.service.FcmMessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {
    private static Logger logger = LoggerFactory.getLogger(DummyController.class);

    private final FcmMessagingService fcmMessagingService;

    public DummyController(FcmMessagingService fcmMessagingService) {
        this.fcmMessagingService = fcmMessagingService;
    }

    @PostMapping("/article/test")
    public ResponseEntity<String> write(@RequestBody String body) {
        logger.debug("/article/test)");
        return new ResponseEntity<>("Authentication Test", HttpStatus.OK);
    }

    @PostMapping("/devices/{deviceToken}")
    public String pushNotification(@PathVariable String deviceToken) {
        // 기기 토큰을 받고 메시지를 발행하는 테스트 컨트롤러
        System.out.println(deviceToken);
        String res = fcmMessagingService
                .sendMessageToToken(deviceToken, "hello", "hello!!");
        return res;
    }
}
