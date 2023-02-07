package lightning.gathergo.service;

import lightning.gathergo.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ActiveProfiles("local")
@SpringBootTest
public class UserServiceTest {
    private static Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
    private static String DUMMY_UUID = "705c5b09-bc17-463a-a560-e07e0ac20b23";

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceTest(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    @DisplayName("저장된 유저 비밀번호와 주어진 비밀번호가 일치하는지 확인")
    public void validatePassword() {
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
}
