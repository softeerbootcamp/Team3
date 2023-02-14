package lightning.gathergo.service;

import lightning.gathergo.model.Session;
import lightning.gathergo.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class SchedulerService {

    private final SessionRepository repository;

    //@Value("${session.duration}")
    private Integer duration =3600;

    @Autowired
    public SchedulerService(SessionRepository repository) {
        this.repository = repository;
    }

    public void session_expiration(){
        Map<String, Session> sessions =  repository.getCopyOfSessions();
        for(Map.Entry<String, Session> entry : sessions.entrySet()){
            String sid = entry.getKey();
            Session session = entry.getValue();

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime created = session.getCreateDate();

            Long age = Duration.between(created, now).getSeconds();

            if(age.intValue() > duration){
                repository.deleteSessionBySid(sid);
            }
        }
    }

}
