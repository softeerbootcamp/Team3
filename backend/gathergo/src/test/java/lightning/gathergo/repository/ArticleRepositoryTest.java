package lightning.gathergo.repository;

import lightning.gathergo.model.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;

@ActiveProfiles("local")
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository repo;

    @Test
    void 정상적으로_게시물이_들어가는지_테스트() {
        //given
        Date nowDate = new Date(System.currentTimeMillis());
        Article article = new Article();
        article.setCurr(1);
        article.setTotal(3);
        article.setTitle("나나나난");
        article.setClosed(false);
        article.setContent("내용");
        article.setMeetingDay(nowDate);

        // 일단은 다 nullable

        //when
        repo.save(article.getUuid(), article.getTitle(), article.getImgPath(), article.getCurr(),
                article.getTotal(), article.getClosed(), article.getContent(), article.getMeetingDay());
        Long storedId = repo.getLastInsertedId();

        //then
        Assertions.assertThat(repo.findById(storedId).get().getTitle())
                .isEqualTo(article.getTitle());
    }

    @Test
    void findByUserId() {
    }

    @Test
    void findCurrentRegionArticles() {
    }

    @Test
    void updateArticle() {
    }

    @Test
    void deleteArticle() {
    }
}