package lightning.gathergo.repository;

import lightning.gathergo.model.Article;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface JdbcArticleRepository extends CrudRepository<Article, Long> {

    @Modifying
    @Query("INSERT INTO article (title, imgPath, curr, total, isClosed, content, meetingDay) " +
            "values (:title, :imgPath, :curr, :total, :isClosed, :content, :meetingDay);")
    public void save(String title, String imgPath, int curr,
                         int total, boolean isClosed, String content, Date meetingDay);

    @Query(value = "SELECT id FROM article ORDER BY id DESC LIMIT 1")
    Long getLastInsertedId();

    @Query("SELECT * FROM article WHERE id = :id")
    Optional<Article> findById(Long id);
}
