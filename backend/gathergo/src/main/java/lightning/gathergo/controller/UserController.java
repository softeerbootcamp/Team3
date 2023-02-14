package lightning.gathergo.controller;

import lightning.gathergo.dto.RegionDto;
import lightning.gathergo.model.Session;
import lightning.gathergo.service.SessionService;
import lightning.gathergo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<?> getUserById(@CookieValue String sessionId) {
        String realSessionId = sessionId.trim().split(";")[0];
        Optional<Session> session = sessionService.findSessionBySID(realSessionId);
        if(session.isPresent()){
            session.get().getUserId();
        }


        //return new ResponseEntity<RegionDto.Response>(regionResponse, HttpStatus.FOUND);
    }

}
