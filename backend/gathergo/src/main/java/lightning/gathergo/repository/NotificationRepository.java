package lightning.gathergo.repository;

import lightning.gathergo.model.Notification;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, String> {
    @Modifying
    @Query("insert into notification (articleUuid, title, body,  issueDateTime) VALUES (:articleUuid, :title, :body, now())")
    void save(@Param("articleUuid") String articleUuid, @Param("title") String title, @Param("body") String body);

    @Query("select articleUuid, title, body, issueDateTime from notification where articleUuid=articleUuid")
    List<Notification> findNotificationByArticleId(@Param("articleUuid") String articleUuid);

    @Modifying
    @Query("delete from notification where articleUuid = :articleUuid")
    int deleteByArticleUuid(@Param("articleUuid") String articleUuid);

}
