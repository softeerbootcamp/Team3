package lightning.gathergo.repository;

import lightning.gathergo.model.Article;
import lightning.gathergo.model.Comment;
import lightning.gathergo.model.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer> {

    // id, hostId, title, thumbnail, curr,
    // total, isClosed, content, meetingDay, location,
    // regionId, categoryId, uuid
    @Override
    default <S extends Article> S save(S entity) {
        save(entity.getHostId(),
                entity.getTitle(),
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
    @Query("insert into article (hostId, title, total, isClosed, content, meetingDay, location, regionId, categoryId, uuid) " +
            "values (:hostId, :title, :total, :isClosed, :content, :meetingDay, :location, :regionId, :categoryId, :uuid);")
    public void save(Integer hostId, String title,
                     int total, boolean isClosed, String content, Timestamp meetingDay, String location, int regionId, int categoryId, String uuid);

    @Query(value = "select id from article order by id desc LIMIT 1")
    Integer getLastInsertedId();

    @Query("select uuid from article where id = :id")
    String getUuidById(Integer id);

    @Query("select * from article where id = :id")
    Optional<Article> findById(Integer id);

    @Query("select * from article where uuid = :uuid")
    Optional<Article> findByUuid(String uuid);

    //region 모든 열려있는 게시물 반환
    @Query("select * from article where isClosed = 0 order by id desc")
    List<Article> findAllArticles();

    // 지역만 주어진 경우
    @Query("select * from article where regionId = :regionId and isClosed = 0 order by id desc")
    List<Article> findCurrentRegionArticles(int regionId);

    // 카테고리만 주어진 경우
    @Query("select * from article where categoryId = :categoryId and isClosed = 0 order by id desc")
    List<Article> findArticlesByCategoryId(int categoryId);

    // 지역과 카테고리만 주어진 경우
    @Query("select * from article where regionId = :regionId and categoryId = :categoryId and isClosed = 0 order by id desc")
    List<Article> findArticlesByRegionAndCategory(int regionId, int categoryId);
    // 검색어와 카테고리만 주어진 경우
    @Query("select a.* from article a where categoryId=:categoryId and title like CONCAT('%', :keyword, '%') and isClosed = 0 order by id desc")
    List<Article> findByKeywordAndCategory(String keyword, Integer categoryId);

    // 검색어와 지역만 주어진 경우
    @Query("select a.* from article a where regionId=:regionId and title like CONCAT('%', :keyword, '%') and isClosed = 0 order by id desc")
    List<Article> findByKeywordAndRegion(String keyword, Integer regionId);

    // 검색어만 주어진 경우
    @Query("select a.* from article a where title like CONCAT('%', :keyword, '%') and isClosed = 0 order by id desc")
    List<Article> findByKeyword(String keyword);

    // 검색어와 카테고리, 지역 모두 주어진 경우
    @Query("select a.* from article a where regionId=:regionId and categoryId=:categoryId and title like CONCAT('%', :keyword, '%') and isClosed = 0 order by id desc")
    List<Article> findByKeywordAndRegionAndCategory(String keyword, Integer regionId, Integer categoryId);
    //endregion

    //region 마감임박 게시물 반환
    @Query("select * from article where isClosed = 0 order by meetingDay limit 8")
    List<Article> findImminentArticles();

    // 지역만 주어진 경우
    @Query("select * from article where regionId = :regionId and isClosed = 0 order by meetingDay limit 8")
    List<Article> findImminentArticlesByRegionId(Integer regionId);

    // 카테고리만 주어진 경우
    @Query("select * from article where categoryId = :categoryId and isClosed = 0 order by meetingDay limit 8")
    List<Article> findImminentArticlesByCategoryId(Integer categoryId);

    // 지역과 카테고리 모두 주어진 경우
    @Query("select * from article where categoryId = :categoryId and regionId = :regionId and isClosed = 0 order by meetingDay limit 8")
    List<Article> findImminentArticlesByCategoryIdRegionId(Integer categoryId, Integer regionId);

    //endregion

    @Modifying
    @Query("update article " +
            "set title = :title, total = :total, " +
            "isClosed = :isClosed, content = :content, meetingDay = :meetingDay, location = :location, " +
            "regionId = :regionId, categoryId = :categoryId where id=:id")
    public void updateArticleById(String title, int total, boolean isClosed, String content,
                                  Timestamp meetingDay, String location, int regionId, int categoryId, Integer id);

    @Modifying
    @Query("delete from article where id=:id")
    public void deleteById(Integer id);

    // 게시물에 달린 댓글 조회
    @Query("select * from comment c join article a on c.articleId = a.id where a.uuid = :uuid order by c.date")
    List<Comment> findCommentsByArticleUuid(String uuid);

    @Query("select u.* from user u join article a on u.id = a.hostId where a.uuid=:articleUuid")
    User findUserInfoByArticleHostId(String articleUuid);

    @Query("select * from article where meetingDay=:currentTimestamp and isClosed = 0")
    List<Article> findClosableArticles(String currentTimestamp);
}

