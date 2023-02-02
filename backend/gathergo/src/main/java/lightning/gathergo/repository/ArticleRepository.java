package lightning.gathergo.repository;

import lightning.gathergo.model.Article;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

    // id, hostId, title, thumbnail, curr,
    // total, isClosed, content, meetingDay, location,
    // regionId, categoryId, uuid

    @Override
    default <S extends Article> S save(S entity) {
        save(entity.getHostId(), entity.getTitle(), entity.getThumbnail(), entity.getCurr(), entity.getTotal(), entity.getClosed(),
                entity.getContent(), entity.getMeetingDay(), entity.getLocation(), entity.getRegionId(), entity.getCategoryId(), entity.getUuid());
        return (S) findById(getLastInsertedId()).get();
    }

    @Modifying
    @Query("INSERT INTO article (hostId, title, thumbnail, curr, total, isClosed, content, meetingDay, location, regionId, categoryId, uuid) " +
            "values (:hostId, :title, :thumbnail, :curr, :total, :isClosed, :content, :meetingDay, :location, :regionId, :categoryId, :uuid);")
    public void save(Long hostId, String title, String thumbnail, int curr,
                         int total, boolean isClosed, String content, Date meetingDay, String location, int regionId, int categoryId, String uuid);

    @Query(value = "SELECT id FROM article ORDER BY id DESC LIMIT 1")
    Long getLastInsertedId();

    @Query("SELECT uuid FROM article WHERE id = :id")
    String getUuidById(Long id);

    @Query("SELECT * FROM article WHERE id = :id")
    Optional<Article> findById(Long id);

    @Query("SELECT * FROM article")
    List<Article> findAllArticles();

    @Query("SELECT  * FROM article WHERE regionId = (" +
            "SELECT id FROM region WHERE name = :regionName)")
    List<Article> findCurrentRegionArticles(String regionName);

    @Query("SELECT  * FROM article WHERE categoryId = (" +
            "SELECT id FROM category WHERE title = :category)")
    List<Article> findArticlesByCategory(String category);

    @Query("UPDATE article" +
            "SET title=:title, imgPath=:imgPath, curr=:curr, total=:total, isClosed=:isClosed, " +
            "content=:content, meetingDay=:meetingDay " +
            "WHERE id=:id")
    public void updateArticleById(String title, String imgPath, int curr,
                                  int total, boolean isClosed, String content, Date meetingDay, Long id);

    @Query("DELETE FROM article WHERE id=:id")
    public void deleteById(Long id);
}
