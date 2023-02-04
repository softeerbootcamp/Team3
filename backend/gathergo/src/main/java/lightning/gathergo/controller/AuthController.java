package lightning.gathergo.controller;

import lightning.gathergo.dto.LoginDto;
import lightning.gathergo.dto.SignupDto;
import lightning.gathergo.model.Session;
import lightning.gathergo.model.User;
import lightning.gathergo.service.SessionService;
import lightning.gathergo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto.LoginInput loginDto) {
        Optional<User> user = userService.findUserByUserId(loginDto.getUserId());

        if (user.isEmpty() || !passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
            return new ResponseEntity<>(new LoginDto.LoginFailedResponse("ID나 비밀번호가 일치하지 않습니다", ""), HttpStatus.UNAUTHORIZED);
        }

        Session session = sessionService.createSession(user.get().getUserId(), user.get().getUserName());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // headers.add("Set-Cookie", "sessionId:" + session.getSessionId());

        return new ResponseEntity<>(new LoginDto.LoginSuccessfulResponse("로그인 성공", session, "/"), headers, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupDto.SignupInput signupDto, HttpRequest request) {
        String userId = signupDto.getUserId();
        String password = signupDto.getPassword();
        String email = signupDto.getEmail();
        String UserName = signupDto.getUserName();

        // TODO: Mapper 이용하기

        User user = new User(UUID.randomUUID().toString(), userId, UserName, password, email, "", "");

        try {
            userService.addUser(user);
        } catch (Exception e) {
            logger.error("signUp 실패, {}", e.getMessage());
        }

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        headers.add("Location", "/login");
        return new ResponseEntity<>(new SignupDto.SignupSuccessfulResponse( "회원가입 성공", "/login"), headers, HttpStatus.OK);
    }


        @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getBindingResult().getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
}
