package lightning.gathergo.repository;

import lightning.gathergo.model.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class SessionRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(SessionRepositoryTest.class);

    private SessionRepository sessionRepository = new SessionRepository();

    @Test
    @DisplayName("정상 생성 테스트")
    public void create() {
        // given
        Session s = new Session("a", "username");

        // when
        boolean result = sessionRepository.createSession(s);

        // then
        assertThat(result).isEqualTo(true);
    }
}
