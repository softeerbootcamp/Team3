package lightning.gathergo.service;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class CountService {
    private final RedisTemplate<String,Object> redisTemplate;

    private final HashOperations<String, String, Integer> hashOperations;

    public CountService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    /**
     * 현재 모든 아티클들이 가진 값들 반환 - 메인 페이지에 필요
     * @param
     * @return
     */
    public Map<String,Integer> getCounts(){
        return hashOperations.entries("count");
    }

    /**
     * 해당 모집글에 대한 현재 참여 인원 조회
     * @param articleId // uuid 값으로
     * @return
     */
    public Integer getCount(String articleId){

        //roomId는 first key값, testcaseId는 secondkey값 (내부의 key값)

        Integer countVal = hashOperations.get("count",articleId);

        return countVal;
    }

    /**
     * 새로운 아티클의 현재 참여인원 생성
     *
     * @param articleId, count
     * @param
     * @return
     */
    public Integer createCount(String articleId, Integer count){
        hashOperations.put("count",articleId,count);
        return this.getCount(articleId);
    }

    /**
     * 아티클의 참여 인원 수정
     * @param articleId
     * @param newValue
     * @return
     */
    public Integer modifyCount(String articleId,Integer newValue){
        hashOperations.put("count",articleId,newValue);
        return this.getCount(articleId);
    }

    public void deleteCount(String articleId){
        hashOperations.delete("count",articleId);
    }


}
