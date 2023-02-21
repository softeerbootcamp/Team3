package lightning.gathergo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
public class CountServiceTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Test
    public void testIncrement() throws Exception {
        String key = "testKey";
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisTemplate.getConnectionFactory());

        // Set initial value to 0
        stringRedisTemplate.opsForValue().set(key, "0");

        int numThreads = 100;
        int numIncrementsPerThread = 1000;
        int expectedTotal = numThreads * numIncrementsPerThread;

        CountDownLatch latch = new CountDownLatch(numThreads);

        // Create multiple threads to increment the key
        for (int i = 0; i < numThreads; i++) {
            taskExecutor.execute(() -> {
                for (int j = 0; j < numIncrementsPerThread; j++) {
                    stringRedisTemplate.opsForValue().increment(key, 1L);
                }
                latch.countDown();
            });
        }

        // Wait for all threads to complete
        latch.await(5, TimeUnit.SECONDS);

        // Check the final value of the key
        String finalValue = stringRedisTemplate.opsForValue().get(key);
        int actualTotal = Integer.parseInt(finalValue);
        assertEquals(expectedTotal, actualTotal);
    }
}
