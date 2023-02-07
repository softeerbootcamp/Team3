package lightning.gathergo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SchedulerService {
    @Async
    @Scheduled(cron="0/60 * * * * ?")
    public void cronScheduler() {
        System.out.println(LocalDateTime.now()+" 나는 시스템 시간을 기준으로 1분 마다 주기적으로 실행될거야");
    }
}
