package lightning.gathergo.repository;

import lightning.gathergo.model.Article;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

    // id, hostId, title, thumbnail, curr,
    // total, isClosed, content, meetingDay, location,
    // regionId, categoryId, uuid

    @Override
    default <S extends Article> S save(S entity) {
        save(entity.getHostId(),
                entity.getTitle(),
                entity.getThumbnail(),
                entity.getCurr(),
                entity.getTotal(),
                entity.getClosed(),
                entity.getContent(),
                entity.getMeetingDay(),
                entity.getLocation(),
                entity.getRegionId(),
                entity.getCategoryId(),
                entity.getUuid());
        return (S) findById(getLastInsertedId()).get();
    }

    @Modifying
    @Query("insert into article (hostId, title, thumbnail, curr, total, isClosed, content, meetingDay, location, regionId, categoryId, uuid) " +
            "values (:hostId, :title, :thumbnail, :curr, :total, :isClosed, :content, :meetingDay, :location, :regionId, :categoryId, :uuid);")
    public void save(Long hostId, String title, String thumbnail, int curr,
                         int total, boolean isClosed, String content, Timestamp meetingDay, String location, int regionId, int categoryId, String uuid);

    @Query(value = "select id from article order by id desc LIMIT 1")
    Long getLastInsertedId();

    @Query("select uuid from article where id = :id")
    String getUuidById(Long id);

    @Query("select * from article where id = :id")
    Optional<Article> findById(Long id);

    @Query("select * from article where uuid = :uuid")
    Optional<Article> findByUuid(String uuid);

    @Query("select * from article")
    List<Article> findAllArticles();

    @Query("select * from article where regionId = :regionId")
    List<Article> findCurrentRegionArticles(int regionId);

    @Query("select  * from article where regionId = :regionId and categoryId = :categoryId")
    List<Article> findArticlesByRegionAndCategory(int regionId, int categoryId);


    // id, hostId, title, thumbnail, curr,
    // total, isClosed, content, meetingDay, location,
    // regionId, categoryId, uuid
    @Modifying
    @Query("update article " +
            "set title = :title, thumbnail = :thumbnail, curr = :curr, total = :total, " +
            "isClosed = :isClosed, content = :content, meetingDay = :meetingDay, location = :location, " +
            "regionId = :regionId, categoryId = :categoryId")
    public void updateArticleById(String title, String thumbnail, int curr,
                                  int total, boolean isClosed, String content, Timestamp meetingDay, String location,
                                  int regionId, int categoryId, Long id);

    @Modifying
    @Query("delete from article where id=:id")
    public void deleteById(Long id);
}
