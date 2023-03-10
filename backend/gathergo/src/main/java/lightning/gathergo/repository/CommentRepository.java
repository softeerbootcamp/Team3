package lightning.gathergo.repository;

import lightning.gathergo.model.Comment;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment,Integer> {

    // 저장
    @Modifying
    @Override
    default <S extends Comment> S save(S entity){
        save(entity.getArticleId(), entity.getUserId(), entity.getDate(), entity.getContent(), entity.getUuid());
        return (S) findByUuid(entity.getUuid());
    }

    @Modifying
    @Query("insert into comment (articleId, userId, date, content, uuid) " +
            "values (:articleId, :userId, :date,  :content, :uuid)")
    void save(Integer articleId, Integer userId, Timestamp date, String content, String uuid);

    @Query("select * from comment where uuid=:uuid")
    Comment findByUuid(String uuid);

    @Query("select c.* from (select c.* from comment c where c.uuid=:uuid) " +
            "join user u on c.userId = u.id where u.userId=:userId")
    Optional<Comment> findByUuidAndUserId(String uuid, String userId);

    // 수정
    @Modifying
    @Query("update comment set content = :content, date = :date where uuid = :uuid")
    void updateByUuid(String content, Timestamp date, String uuid);


    // 삭제
    @Modifying
    @Query("delete from comment where uuid=:uuid")
    void deleteByUuid(String uuid);
}
