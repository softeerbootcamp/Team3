package lightning.gathergo.repository;

import lightning.gathergo.model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcArticleRepository.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // C
    public Long addArticle(Article article){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("articles")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("title", article.getTitle());
        params.put("hostId", article.getHostId());
        params.put("curr", article.getCurr());
        params.put("total", article.getTotal());
        params.put("isClosed", article.getClosed());
        params.put("content", article.getContent());
        params.put("meetingDay", article.getMeetingDay().getTime());
        params.put("regionId", article.getRegionId());
        params.put("categoryId", article.getCategoryId());
        Number key = simpleJdbcInsert.executeAndReturnKey(params);

        return key.longValue();
    }

    // R

    // U

    // D
}
