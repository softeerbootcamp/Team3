package lightning.gathergo.service;

import lightning.gathergo.model.Session;
import lightning.gathergo.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class SessionService {
    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session createSession(String userId, String userName) throws IllegalStateException {

        Session s = new Session(userId, userName);
        sessionRepository.createSession(s);

        return s;
    }

    public Optional<Session> findSessionBySID(String sid) {
        return sessionRepository.findSessionBySid(sid);
    }

    // TODO: Filter에서 수행
    public String extractSidFromCookie(HttpRequest request) {
        HttpHeaders requestHeader = request.headers();

        if(requestHeader.firstValue("Cookie").isPresent()) {
            String sid = String.valueOf(requestHeader.firstValue("Cookie"));
            sid = Stream.of(sid.split(" "))
                    .map(String::trim)
                    .filter((str) -> str.contains("sid="))
                    .findFirst().orElse("");

            if(sid.charAt(sid.length()-1) == ';') {
                sid = sid.substring(0, sid.length()-1);
            }

            if(!sid.isBlank()) {
                sid = sid.split("=")[1];
                return sid;
            }
        }
        return null;
    }
}

