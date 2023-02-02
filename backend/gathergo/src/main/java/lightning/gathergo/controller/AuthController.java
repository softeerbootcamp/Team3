package lightning.gathergo.controller;

import lightning.gathergo.dto.LoginDto;
import lightning.gathergo.model.Session;
import lightning.gathergo.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class AuthController {
    private final SessionService sessionService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(SessionService sessionService, PasswordEncoder passwordEncoder) {
        this.sessionService = sessionService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginDto.LoginInput loginDto) {
        String userId = loginDto.getUserId();
        String password = loginDto.getPassword();

        // TODO: User DB 참조해 userName 가져오기
        String userName = "";
        String encodedPassword = "";

        // TODO: User DB 참조해 password 비교하기
        if(!passwordEncoder.matches(password, encodedPassword))
            return new ResponseEntity<>(new LoginDto.LoginFailedResponse( "ID나 비밀번호가 일치하지 않습니다", ""), HttpStatus.BAD_REQUEST);

        Session session = sessionService.createSession(userId, userName);

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        headers.add("Set-Cookie", session.getSessionId());
        return new ResponseEntity<>(new LoginDto.LoginSuccessfulResponse( "로그인 성공", session, "/"), headers, HttpStatus.OK);
    }
}
