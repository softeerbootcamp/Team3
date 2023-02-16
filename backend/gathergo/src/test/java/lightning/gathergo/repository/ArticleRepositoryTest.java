/*
package lightning.gathergo.repository;

import lightning.gathergo.model.Article;
import org.assertj.core.api.AssertionInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@ActiveProfiles("local")
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository repo;

    @BeforeEach
    void setting(){
        repo.deleteAll();
    }

    @Test
    void 정상적으로_게시물이_들어가는지_테스트() {
        //given
        Timestamp nowDate = new Timestamp(System.currentTimeMillis());
        Article article = new Article();
        article.setTotal(3);
        article.setTitle("나나나난");
        article.setClosed(false);
        article.setContent("내용");
        article.setMeetingDay(nowDate);

        // 일단은 다 nullable

        //when
        repo.save(article);
        Integer storedId = repo.getLastInsertedId();

        //then
        Assertions.assertThat(repo.findById(storedId).get().getTitle())
                .isEqualTo(article.getTitle());
    }
    @Test
    void 지역아이디로_찾은_게시물이_진짜로_해당_지역의_모임글인지_확인() {
        // given
        Article a1 = new Article(); a1.setRegionId(1); a1.setTitle("test1"); a1.setCurr(1); a1.setCategoryId(123);
        Article a2 = new Article(); a2.setRegionId(2);
        Article a3 = new Article(); a3.setRegionId(3);
        Article a4 = new Article(); a4.setRegionId(1); a4.setTitle("test"); a4.setCurr(2);

        repo.save(a1);
        repo.save(a2);
        repo.save(a3);
        repo.save(a4);

        // when
        List<Article> region1Articles = repo.findCurrentRegionArticles(1);
        // 1로 기껏 찾아놓고 왜 0이 되는건지 확인하기

        // then
        Assertions.assertThat(region1Articles.size()).isEqualTo(2);
        Assertions.assertThat(region1Articles.get(0).getRegionId())
                .isEqualTo(a1.getRegionId());
        Assertions.assertThat(region1Articles.get(1).getRegionId())
                .isEqualTo(region1Articles.get(1).getRegionId());
    }

    @Test
    void 수정이_의도한_대로_되는지_확인() {
        // given
        Article article = new Article();
        article.setTitle("변경 전");
        Integer id = repo.save(article).getId();

        //when
        repo.updateArticleById("변경 후", 0, false, null, null, null, 0, 0, id);

        //then
        Assertions.assertThat(repo.findById(id).get().getTitle())
                .isEqualTo("변경 후");
    }

    @Test
    void 게시물_하나_지우는_기능이_잘_되는지_테스트() {
        // given
        Article article = new Article();
        Article nullArticle = new Article();
        article.setTitle("dummy");
        repo.save(article);
        Integer dummyId = repo.getLastInsertedId();

        // when
        repo.deleteById(dummyId);

        // then
        Assertions.assertThat(repo.findById(dummyId).orElse(nullArticle))
                .isEqualTo(nullArticle);
    }

    @Test
    void 게시물_전부_지우기_테스트(){
        //given
        Article a1 = new Article(); a1.setRegionId(1); a1.setTitle("test1"); a1.setCurr(1); a1.setCategoryId(123);
        Article a2 = new Article(); a2.setRegionId(2);
        Article a3 = new Article(); a3.setRegionId(3);
        Article a4 = new Article(); a4.setRegionId(1); a4.setTitle("test"); a4.setCurr(2);

        repo.save(a1);
        repo.save(a2);
        repo.save(a3);
        repo.save(a4);

        //when
        repo.deleteAll();

        //then
        Assertions.assertThat(repo.findAllArticles().size())
                .isEqualTo(0);
    }

    void Uuid로_잘_얻어와지는지_테스트(){
        //given
        Article article = new Article();
        String uuid = "testestestest";
        article.setUuid(uuid);

        //when
        repo.save(article);

        //then
        Assertions.assertThat(repo.findByUuid(uuid))
                .isEqualTo(uuid);
    }
}*/
