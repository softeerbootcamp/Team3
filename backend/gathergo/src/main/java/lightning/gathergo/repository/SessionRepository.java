package lightning.gathergo.repository;

import lightning.gathergo.model.Session;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionRepository {
    /*
     * key: Session id
     * value: Session
     */
    protected Map<String, Session> sessions = new ConcurrentHashMap<String, Session>() {
    };  // 테스트 위해 protected

    public Optional<Session> findSessionBySid(String sid) {
        return Optional.ofNullable(this.sessions.get(sid));
    }

    public boolean createSession(Session s) {
        return this.sessions.put(s.getSessionId(), s) == null;
    }

    public Session deleteSessionBySid(String sid) {
        return this.sessions.remove(sid);
    }

    public Optional<Collection<Session>> getSessions() {
        return Optional.of(this.sessions.values());
    }

    public Map<String, Session> getCopyOfSessions(){
        Map<String, Session> deepCopiedSessions = new ConcurrentHashMap<String, Session>();
        for (Map.Entry<String, Session> entry : sessions.entrySet()) {
            String key = entry.getKey();
            Session value = entry.getValue();
            deepCopiedSessions.put(key, value);
        }
        return deepCopiedSessions;
    }
}
