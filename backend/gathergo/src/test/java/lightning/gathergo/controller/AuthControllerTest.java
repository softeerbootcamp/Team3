package lightning.gathergo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lightning.gathergo.dto.SignupDto;
import lightning.gathergo.model.Session;
import lightning.gathergo.model.User;
import lightning.gathergo.repository.SessionRepository;
import lightning.gathergo.service.CookieService;
import lightning.gathergo.service.SessionService;
import lightning.gathergo.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

import static lightning.gathergo.service.CookieService.SESSION_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class AuthControllerTest {
    private final Logger logger = LoggerFactory.getLogger(AuthControllerTest.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private final UserService userService;

    private final SessionService sessionService;

    private final SessionRepository sessionRepository;

    private final CookieService cookieService;

    private static String DUMMY_UUID = "705c5b09-bc17-463a-a560-e07e0ac20b23";

    @Autowired
    public AuthControllerTest(UserService userService, SessionService sessionService, SessionRepository sessionRepository, CookieService cookieService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.sessionRepository = sessionRepository;
        this.cookieService = cookieService;
    }

    @Test
    @DisplayName("로그인 시 Session 정보 json으로 발급 확인")
    public void login() throws Exception {
        // given
        User user = new User(DUMMY_UUID, "asdf", "gildong", "12345678", "asdf@gmail.com", "", "");
        userService.addUser(user);

        // when
        this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"userId\": \"asdf\",\n" +
                                "\"password\": \"12345678\"\n" +
                                "}")
                ).andDo(print())
        // then
                .andExpect(content().string(containsString("sessionId")))  // 세션 발급
                .andExpect(content().string(containsString("asdf"))); // userId, userName 반환
    }

    @Test
    @DisplayName("로그인 시 Session 정보 생성 확인")
    public void loginWithSessionCheck() throws Exception {
        // given
        User user = new User(DUMMY_UUID, "asdf", "gildong", "12345678", "asdf@gmail.com", "", "");
        userService.addUser(user);

        // when
        this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"userId\": \"asdf\",\n" +
                                "\"password\": \"12345678\"\n" +
                                "}")
                ).andDo(print());

        assertThat(sessionRepository.getSessions().get().stream().anyMatch(s -> s.getUserId().equals("asdf"))).isTrue();
                // then
    }

    @Test
    @DisplayName("회원가입 확인")
    public void signup() throws Exception {
        // given
        SignupDto.SignupInput input = new SignupDto.SignupInput("asdf", "gildong", "12345678", "gildong@gmail.com");

        String jsonInput = objectMapper.writeValueAsString(input);
        logger.info(jsonInput);

        // when
        this.mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput)
                ).andDo(print())
                // then
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/login"));  // 리다이렉트

    }

    @Test
    @DisplayName("로그인 필요한 더미 api 접속에 session 없으면 redirect 확인")
    public void writeWithoutAuthentication() throws Exception {
        this.mockMvc.perform(post("/api/write")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("hello")
                ).andDo(print())
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/login"));
    }

    @Test
    @DisplayName("로그인 필요한 더미 api 접속에 session 있으면 response 확인")
    public void writeWithAuthentication() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();

        // given
        Session createdSession = sessionService.createSession("asdf", "gildong");  // 세션 생성

        cookieService.createSessionCookie(SESSION_ID, createdSession.getSessionId(), 0, response); // 쿠키 생성
        // when
        Cookie foundCookie = response.getCookie(SESSION_ID);

        // then
        assert foundCookie != null;

        this.mockMvc.perform(post("/api/write").cookie(foundCookie)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("hello")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("write test api")));
    }

    @Test
    @DisplayName("로그아웃 테스트")
    public void logout() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();

        // given
        Session createdSession = sessionService.createSession("asdf", "gildong");  // 세션 생성

        cookieService.createSessionCookie(SESSION_ID, createdSession.getSessionId(), 3600, response); // 쿠키 생성
        // when
        Cookie foundCookie = response.getCookie(SESSION_ID);

        // then
        assert foundCookie != null;

        this.mockMvc.perform(delete("/logout").cookie(foundCookie)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("hello")
                )
                .andExpect(status().isFound())
                .andExpect(cookie().exists(SESSION_ID))
                .andExpect(cookie().value(SESSION_ID, ""));
    }
}
