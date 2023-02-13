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
    @Query("insert into subscription (articleId, token) VALUES (:articleId, :token)")
    void save(@Param("articleId") int articleId, @Param("token") String deviceToken);

    @Query("select articleId, token from subscription")
    List<Subscription> findAll();

    @Modifying
    @Query("delete from subscription where articleId = :articleId")
    void deleteByArticleId(@Param("articleId") int articleId);

    @Modifying
    @Query("delete from subscription where token = :token")
    void deleteByDeviceToken(@Param("token") int deviceToken);

    @Query("select articleId, token from subscription where articleId = :articleId")
    List<Subscription> findByArticleId(int articleId);
}
