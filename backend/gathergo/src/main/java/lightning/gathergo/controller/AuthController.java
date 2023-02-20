package lightning.gathergo.controller;

import lightning.gathergo.dto.CommonResponseDTO;
import lightning.gathergo.dto.LoginDto;
import lightning.gathergo.dto.SignupDto;
import lightning.gathergo.mapper.SignupDtoMapper;
import lightning.gathergo.model.Session;
import lightning.gathergo.model.User;
import lightning.gathergo.service.CookieService;
import lightning.gathergo.service.SessionService;
import lightning.gathergo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final SessionService sessionService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final SignupDtoMapper signupDtoMapper;
    private final CookieService cookieService;

    private int cookieExpiration = 3600;

    @Autowired
    public AuthController(SessionService sessionService, PasswordEncoder passwordEncoder, UserService userService, SignupDtoMapper signupDtoMapper, CookieService cookieService) {
        this.sessionService = sessionService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.signupDtoMapper = signupDtoMapper;
        this.cookieService = cookieService;
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDTO<?>> login(@RequestBody LoginDto.LoginInput loginDto, HttpServletResponse response, CookieService cookieService) {
        User loginUser = userService.loginUser(loginDto);

        if(loginUser == null)
            return new ResponseEntity<>(new CommonResponseDTO<>(0, "ID나 비밀번호가 일치하지 않습니다", new LoginDto.LoginFailedResponse("ID나 비밀번호가 일치하지 않습니다")), HttpStatus.UNAUTHORIZED);

        Session session = sessionService.createSession(loginUser.getUserId(), loginUser.getUserName(), loginUser.getUuid());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        cookieService.createSessionCookie(CookieService.SESSION_ID, session.getSessionId(), response);

        return new ResponseEntity<>(new CommonResponseDTO<>(1, "로그인 성공", new LoginDto.LoginSuccessfulResponse("로그인 성공", session)), headers, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupDto.SignupInput signupDto) {
        User user = signupDtoMapper.toUser(signupDto);

        userService.addUser(user);  // 중복 시 409, DUPLICATE_KEY

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(new CommonResponseDTO<>(1, "회원가입 성공", new SignupDto.SignupSuccessfulResponse( "회원가입 성공")), headers, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        cookieService.invalidateSession(request.getCookies(), response);

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(new CommonResponseDTO<>(1, "로그아웃 성공", new SignupDto.SignupSuccessfulResponse( "로그아웃 성공")), headers, HttpStatus.OK);
    }

        @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getBindingResult().getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
}
