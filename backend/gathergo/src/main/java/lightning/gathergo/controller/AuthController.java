package lightning.gathergo.controller;

import lightning.gathergo.dto.LoginDto;
import lightning.gathergo.model.Session;
import lightning.gathergo.model.User;
import lightning.gathergo.service.SessionService;
import lightning.gathergo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@RestController
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final SessionService sessionService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public AuthController(SessionService sessionService, PasswordEncoder passwordEncoder, UserService userService) {
        this.sessionService = sessionService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginDto.LoginInput loginDto) {
        Optional<User> user = userService.findUserByUserId(loginDto.getUserId());

        if (user.isEmpty() || !passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
            return new ResponseEntity<>(new LoginDto.LoginFailedResponse("ID나 비밀번호가 일치하지 않습니다", ""), HttpStatus.BAD_REQUEST);
        }

        Session session = sessionService.createSession(user.get().getUserId(), user.get().getUserName());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // headers.add("Set-Cookie", "sessionId:" + session.getSessionId());

        return new ResponseEntity<>(new LoginDto.LoginSuccessfulResponse("로그인 성공", session, "/"), headers, HttpStatus.OK);
    }
}
