package lightning.gathergo.repository;

import lightning.gathergo.model.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JDBCArticleRepositoryTest {

    @Autowired
    private JdbcArticleRepository repo;

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
        //Article stored = repo.addArticle(article);
        repo.save(article.getTitle(), article.getImgPath(), article.getCurr(),
                article.getTotal(), article.getClosed(), article.getContent(), article.getMeetingDay());

        //then
        //Assertions.assertThat(stored.getId()).isEqualTo(article.getId());
        //Assertions.assertThat(stored).isEqualTo(article);
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