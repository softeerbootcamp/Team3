package lightning.gathergo.repository;

import lightning.gathergo.model.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class SessionRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(SessionRepositoryTest.class);

    private SessionRepository sessionRepository;

    @BeforeEach
    public void init() {
        sessionRepository = new SessionRepository();
    }

    @Test
    @DisplayName("정상 생성 테스트")
    public void create() {
        // given
        Session s = new Session(1, "a", "username");

        // when
        boolean result = sessionRepository.createSession(s);

        // then
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("없는 세션 삭제 테스트")
    public void deleteUnValidSession() {
        // given

        // when
        Session result = sessionRepository.deleteSessionBySid(String.valueOf(UUID.randomUUID()));

        // then
        assertThat(result).isEqualTo(null);
    }

    @Test
    @DisplayName("있는 세션 삭제 테스트")
    public void deleteValidSession() {
        // given
        String sid = String.valueOf(UUID.randomUUID());
        Session validSession = new Session(1, "asdf", "asdf");
        sessionRepository.sessions.put(sid, validSession);

        // when
        Session result = sessionRepository.deleteSessionBySid(sid);

        // then
        assertThat(result).isEqualTo(validSession);
    }
}
