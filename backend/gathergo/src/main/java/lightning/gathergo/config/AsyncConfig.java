package lightning.gathergo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig implements AsyncConfigurer, SchedulingConfigurer {

    /**
     * ThreadPoolTaskScheduler는 태스크실행 및 스케줄링에 사용되는 스프링 라이브러리 입니다.
     * 배치 관리 할때 Thread를 개발자가 직접 제어하지 않고 실행하고 싶은 Task와 시간을 Pool에 넣음
     * 그러면 ThreadPoolTaskScheduler에서 제어하여 해당 시간에 맞춰서 Thread를 생성 및 실행 시키고 종료 까지 해줌
     *
     */
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        scheduler.setThreadNamePrefix("MY-SCHEDULER-");
        scheduler.initialize();
        return scheduler;
    }
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(this.threadPoolTaskScheduler());
    }

    @Override
    public Executor getAsyncExecutor() { return this.threadPoolTaskScheduler(); }


}
