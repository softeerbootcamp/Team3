package lightning.gathergo.service;

import lightning.gathergo.model.Session;
import lightning.gathergo.repository.SessionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("local")
class SchedulerServiceTest {

    @Mock
    private SessionRepository repository;


    @InjectMocks
    private SchedulerService schedulerService;

    @Test
    public void testSessionExpiration() {
        //given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime created = now.minusDays(2);

        Session session = new Session("sid","userId","userName",created);
        Map<String, Session> sessions =  new ConcurrentHashMap<String, Session>();
        sessions.put("sid", session);
        when(repository.getCopyOfSessions()).thenReturn(sessions);
        when(repository.deleteSessionBySid("sid")).thenReturn(session);

        //when
        schedulerService.session_expiration();

        //then
        verify(repository, times(1)).deleteSessionBySid("sid");

    }
}
