package lightning.gathergo.repository;

import lightning.gathergo.model.Article;
import lightning.gathergo.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@SpringBootTest
@Transactional
class UserArticleRelationshipRepositoryTest {


    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserArticleRelationshipRepository userArticleRelationshipRepository;

    @Test
    void 호스팅하는_게시물이_잘_얻어와지는지() {

        User user1 = new User(String.valueOf(UUID.randomUUID()), "asdf1", "gildong1", "123456", "asdf1@gmail.com", "", "");
        userRepository.save(user1);

        // given
        List<User> users = userRepository.findAll();
        final Integer hostId = users.get(0).getId();
        users.forEach(System.out::println);

        Article article = new Article(hostId, "test", 4, false, "test content", new Timestamp(System.currentTimeMillis()), "서울" ,0, 0, String.valueOf(UUID.randomUUID()));

        // when
        articleRepository.save(article);
        articleRepository.findAll().forEach(System.out::println);
        // then

        List<Article> articles = userRepository.findHostingArticlesById(hostId);
        Assertions.assertThat(articles.get(0).getHostId())
                        .isEqualTo(hostId);

        System.out.println(articles.size());
        articles.forEach(System.out::println);

    }

    @Test
    void 참여하는_게시물이_잘_얻어와지는지() {

        User user1 = new User(String.valueOf(UUID.randomUUID()), "asdf1", "gildong1", "123456", "asdf1@gmail.com", "", "");
        User user2 = new User(String.valueOf(UUID.randomUUID()), "asdf2", "gildong2", "123456", "asdf2@gmail.com", "", "");
        userRepository.save(user1);
        userRepository.save(user2);

        // given
        List<User> users = userRepository.findAll();
        users.forEach(System.out::println);

        final Integer hostId = users.get(0).getId();  // 1
        final Integer myId = users.get(1).getId();  // 2

        // 호스트가 hostId인 게시글
        Article article = new Article(hostId, "testste", 4, false, "test content", new Timestamp(System.currentTimeMillis()), "서울" ,0, 0, String.valueOf(UUID.randomUUID()));
        articleRepository.save(article);

        Integer insertedArticleId = articleRepository.getLastInsertedId();
        assert insertedArticleId != null;

        // when
        userArticleRelationshipRepository.save(myId, insertedArticleId);
        List<Article> articles = userRepository.findParticipatingArticlesById(myId);  // 참여하는 글시게

        // then
        assertThat(articles).isNotNull();
        Assertions.assertThat(articles.get(0).getId())
                .isEqualTo(userArticleRelationshipRepository.findByUserId(myId).get(0).getArticleId());

        System.out.println(articles.size());
        articles.forEach(System.out::println);

    }

    @Test
    void 참여하는_사람이_여럿인_경우(){
        // given
        // user 생성
        User user1 = new User(String.valueOf(UUID.randomUUID()), "asdf1", "gildong1", "123456", "asdf1@gmail.com", "", "");
        User user2 = new User(String.valueOf(UUID.randomUUID()), "asdf2", "gildong2", "123456", "asdf2@gmail.com", "", "");
        User user3 = new User(String.valueOf(UUID.randomUUID()), "asdf3", "gildong3", "123456", "asdf3@gmail.com", "", "");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        List<User> users = userRepository.findAll();
        assert users!= null;
        users.forEach(System.out::println);

        int hostId = users.get(users.size()-1).getId();

        // 호스트가 hostId인 게시글
        Article article = new Article(hostId, "testste", 4, false, "test content", new Timestamp(System.currentTimeMillis()), "서울" ,0, 0, String.valueOf(UUID.randomUUID()));
        articleRepository.save(article);
        Integer insertedArticleId = articleRepository.getLastInsertedId();
        assert insertedArticleId != null;

        assertThat(users.
                get(0).
                getId()).isNotNull().isNotZero();

        assertThat(users.
                get(1).
                getId()).isNotNull().isNotZero();

        assertThat(users.
                get(2).
                getId()).isNotNull().isNotZero();

        // 참여자
        userArticleRelationshipRepository.save(
                users.
                        get(0).
                        getId(),
                Math.toIntExact(insertedArticleId));
        userArticleRelationshipRepository.save(users.get(1).getId(), Math.toIntExact(insertedArticleId));
        userArticleRelationshipRepository.save(users.get(2).getId(), Math.toIntExact(insertedArticleId));

        // then
        List<Article> articles1 = userRepository.findParticipatingArticlesById(users.get(0).getId());  // 참여하는 글시게
        assertThat(articles1.size()).isNotZero();

        List<Article> articles2 = userRepository.findParticipatingArticlesById(users.get(1).getId());  // 참여하는 글시게
        assertThat(articles2.size()).isNotZero();

        List<Article> articles3 = userRepository.findParticipatingArticlesById(users.get(2).getId());  // 참여하는 글시게
        assertThat(articles3.size()).isNotZero();
    }
}