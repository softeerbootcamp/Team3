package lightning.gathergo.service;

import lightning.gathergo.model.Session;
import lightning.gathergo.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class SchedulerService {

    private final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    private final SessionRepository repository;

    @Value("${session.duration}")
    private Integer duration;//=3600;

    @Autowired
    public SchedulerService(SessionRepository repository) {
        this.repository = repository;
    }

    @Scheduled(cron="0 0 02 * * ?") //매일 새벽 2시마다 실행
    public void session_expiration(){
        logger.info(LocalDateTime.now().toString()+": session_expiration method");
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
