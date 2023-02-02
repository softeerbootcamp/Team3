package lightning.gathergo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {
    private static Logger logger = LoggerFactory.getLogger(DummyController.class);
    @PostMapping("/api/write")
    public ResponseEntity<String> write(@RequestBody String body) {
        logger.debug("/api/write");
        return new ResponseEntity<>("write test api", HttpStatus.OK);
    }
}
