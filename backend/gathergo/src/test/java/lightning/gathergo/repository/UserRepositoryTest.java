package lightning.gathergo.repository;

import lightning.gathergo.model.User;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("userId로 User 찾기 테스트")
    public void testFindByUserId() {
        // given
        User user = new User("uuid", "userId", "userName", "password", "email", "intro", "profilePath");
        userRepository.save(user);

        // when
        Optional<User> found = userRepository.findUserByUserId("userId");

        // then
        assertThat(found.isPresent()).as("userId: %s 탐색 결과 확인", "userId").isEqualTo(true);

        RecursiveComparisonConfiguration configuration = RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id")
                .build();

        assertThat(found.get()).usingRecursiveComparison(configuration).isEqualTo(user);
    }
}
