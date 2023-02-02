package lightning.gathergo.controller;

import lightning.gathergo.model.User;
import lightning.gathergo.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(AuthControllerTest.class);

    private static String DUMMY_UUID = "705c5b09-bc17-463a-a560-e07e0ac20b23";

    @Autowired
    public AuthControllerTest(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    @DisplayName("저장된 유저 비밀번호와 주어진 비밀번호가 일치하는지 확인")
    public void validatePassword() throws Exception {
        final String rawPassword = "12345678";

        // given
        User user = new User(DUMMY_UUID, "asdf", "gildong", rawPassword, "asdf@gmail.com", "", "");
        userService.addUser(user);

        // when
        Optional<User> found = userService.findUserByUserId("asdf");

        // then
        assertThat(found.isPresent()).as("userId 검색 결과 %s", found.get()).isTrue();
        assertThat(passwordEncoder.matches(rawPassword, user.getPassword())).isTrue();

    }

    @Test
    @DisplayName("로그인시 Session 정보 json으로 발급 확인")
    public void login() throws Exception {
        // given
        User user = new User(DUMMY_UUID, "asdf", "gildong", "12345678", "asdf@gmail.com", "", "");
        userService.addUser(user);

        // when
        this.mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"userId\": \"asdf\",\n" +
                                "\"password\": \"12345678\"\n" +
                                "}")
                ).andDo(print())
        // then
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("sessionId")))  // 세션 발급
                .andExpect(content().string(containsString("asdf"))); // userId, userName 반환

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
        this.mockMvc.perform(post("/api/write")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("hello")
                        .header("sessionId", DUMMY_UUID)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("write test api")));
    }
}
