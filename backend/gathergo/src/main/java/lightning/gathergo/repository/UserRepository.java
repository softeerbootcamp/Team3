package lightning.gathergo.repository;

import lightning.gathergo.model.Article;
import lightning.gathergo.model.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value="select * from user u where u.userId = :userId")
    Optional<User> findUserByUserId(String userId);

    @Modifying
    @Query(value="delete from user u where u.userId = :userId")
    void deleteUserByUserId(String userId);

    @Query(value="select * from user u")
    List<User> findAll();

    // 해당 유저가 게스트로 참여하는 글 조회
    @Query(value = "select a.* from article a where a.id in (select us.articleId from user u join user_article_relationship us on :id = us.userId)")
    List<Article> findParticipatingArticlesById(Integer id);

    // 호스팅 모임 조회
    @Query(value = "select a.* from article a where :id = a.hostId")
    List<Article> findHostingArticlesById(Integer id);

    @Query("select * from user where uuid = :uuid")
    User findByUuid(String uuid);

    @Query(value="insert into user (userId, userName, password, email)")
    void save(String userId, String userName, String password, String email);

    @Modifying
    @Query(value="insert into user (uuid, userId, userName, password, email, introduction, profilePath) values(:uuid, :userId, :userName, :password, :email, :introduction,  :profilePath)")
    void save(@Param("uuid") String uuid, @Param("userId") String userId, @Param("userName") String userName, @Param("password") String password, @Param("email") String email, @Param("introduction") String introduction, @Param("profilePath") String profilePath);
}
