package lightning.gathergo.repository;

import lightning.gathergo.model.Notification;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, String> {
    @Modifying
    @Query("insert into notification (articleUuid, title, body, issueDateTime) VALUES (:articleUuid, :title, :body, now())")
    void save(@Param("articleUuid") String articleUuid, @Param("title") String title, @Param("body") String body);

    @Modifying
    @Query("insert into notification (articleUuid, title, body, issueDateTime) VALUES (:articleUuid, :title, :body, :issueDateTime)")
    void save(@Param("articleUuid") String articleUuid, @Param("title") String title, @Param("body") String body, @Param("issueDateTime") Timestamp issueDateTime);

    @Query("select articleUuid, title, body, issueDateTime from notification where articleUuid=:articleUuid")
    List<Notification> findByArticleId(@Param("articleUuid") String articleUuid);

    @Query("select articleUuid, title, body, issueDateTime from notification where articleUuid in (:articleUuid)")
    List<Notification> findByArticleIds(@Param("articleUuid") List<String> articleUuid);

    @Modifying
    @Query("delete from notification where articleUuid = :articleUuid")
    int deleteByArticleUuid(@Param("articleUuid") String articleUuid);

    @Modifying
    @Query("delete from notification where articleUuid in (:articleUuid)")
    int deleteByArticleUuids(@Param("articleUuid") List<String> articleUuid);

}
