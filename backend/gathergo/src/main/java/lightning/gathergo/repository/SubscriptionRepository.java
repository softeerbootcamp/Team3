package lightning.gathergo.repository;

import lightning.gathergo.model.Subscription;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, String> {
    @Modifying
    @Query("insert into subscription (articleId, token) VALUES (:uuid, :token)")
    void save(@Param("uuid") String articleUuid, @Param("token") String deviceToken);

    @Query("select articleId, token from subscription")
    List<Subscription> findAll();

    @Modifying
    @Query("delete from subscription where articleId = :uuid")
    int deleteByArticleId(@Param("uuid") String articleUuid);

    @Modifying
    @Query("delete from subscription where articleId in (:uuid)")
    int deleteByArticleIds(@Param("uuid") List<String> articleUuid);

    @Modifying
    @Query("delete from subscription where token = :token")
    int deleteByDeviceToken(@Param("token") String deviceToken);

    @Query("select articleId, token from subscription where articleId = :uuid")
    List<Subscription> findByArticleId(@Param("uuid") String articleUuid);

    @Modifying
    @Query("delete from subscription where articleId = :uuid and token = :token")
    int deleteByArticleIdAndToken(@Param("uuid") String articleUuid, @Param("token") String deviceToken);

}
