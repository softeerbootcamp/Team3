package lightning.gathergo.repository;

import lightning.gathergo.model.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@Transactional
@SpringBootTest
class JDBCArticleRepositoryTest {

    JdbcArticleRepository repo;

    private DataSource dataSource (){
        var dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/gathergo?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true")
                .username("root")
                .password("1234")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
        return dataSource;
    }

    @BeforeEach
    void setUp() {
        repo = new JdbcArticleRepository(dataSource());
    }
    @Test
    void 정상적으로_게시물이_들어가는지_테스트() {
        //given

        Date nowDate = new Date(System.currentTimeMillis());
        Article article = new Article();
        article.setHostId(new Long(1));
        article.setCurr(1);
        article.setTotal(3);
        article.setTitle("나나나난");
        article.setClosed(false);
        article.setContent("내용");
        article.setRegionId(0);
        article.setCategoryId(0);
        article.setMeetingDay(nowDate);

        //when
        Article stored = repo.addArticle(article);

        //then
        Assertions.assertThat(stored.getId()).isEqualTo(article.getId());
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