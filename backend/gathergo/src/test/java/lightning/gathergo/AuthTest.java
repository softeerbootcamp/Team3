package lightning.gathergo;

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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class AuthTest {
    @Autowired
    private MockMvc mockMvc;

    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(AuthTest.class);

    private static String DUMMY_SESSION = "705c5b09-bc17-463a-a560-e07e0ac20b23";

    @Autowired
    public AuthTest(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /*private class User {
        String userId;
        String userName;
        String password;

        public User(String userId, String userName, String password) {
            this.userId = userId;
            this.userName = userName;
            this.password = password;
        }
    }
    private class UserRepository {
        private Map<String, User> users = new HashMap<>();  // userId,
    }
    private final UserRepository userRepository = new UserRepository();*/

    @Test
    @DisplayName("로그인시 Session 정보 json으로 발급 확인")
    public void login() throws Exception {
        String userId = "asdf";
        String encodedPassword = passwordEncoder.encode("password");

        logger.debug("login - encodedPassword {}", encodedPassword);
        // userRepository.users.put(userId, new User(userId, "gildong", encodedPassword));


        this.mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"userId\": \"asdf\",\n" +
                                "\"password\": \"password\"\n" +
                                "}")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("sessionId")))
                .andExpect(content().string(containsString("asdf")));
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
                        .header("sessionId",DUMMY_SESSION)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("write test api")));
    }
}
