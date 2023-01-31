package lightning.gathergo.repository;

import lightning.gathergo.model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcArticleRepository.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // C
    public Article addArticle(Article article){
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
        article.setId(key.longValue());

        return article;
    }

    // R
    public List<Article> findCurrentRegionArticles(int regionId)
    {
        String query = "select * from articles where regionId = ?";

        List<Article> result = jdbcTemplate.query(query, articleRowMapper(), regionId);
        return result;
    }

    public List<Article> findAllArticles()
    {
        String query = "select * from articles";

        return jdbcTemplate.query(query, articleRowMapper());
    }

    public List<Article> findArticlesByHostId(int hostId)
    {
        String query = "select * from articles where hostId = ?";

        List<Article> result = jdbcTemplate.query(query, articleRowMapper(), hostId);
        return result;
    }

    public Article findById(Long articleId){
        String query = "select * from articles where id = ?";

        Article result = jdbcTemplate.queryForObject(query, articleRowMapper(), articleId);
        return result;
    }

    // U
    public Article updateArticle(Article article){
        String query = "update articles set title = ?, curr = ?, total = ?, isClosed = ?," +
                "content = ?, meetingDay = ?, regionId = ?, categoryId = ? where id = ?";
        Long articleId = article.getId();

        Map<String, Object> params = new HashMap<>();
        params.put("title", article.getTitle());
        params.put("curr", article.getCurr());
        params.put("total", article.getTotal());
        params.put("isClosed", article.getClosed());
        params.put("content", article.getContent());
        params.put("meetingDay", article.getMeetingDay().getTime());
        params.put("regionId", article.getRegionId());
        params.put("categoryId", article.getCategoryId());
        jdbcTemplate.update(query, params);

        return findById(article.getId());
    }

    // D
    public void deleteArticle(Long articleId){
        String query = "delete from articles where id = ?";
        jdbcTemplate.update(query, articleId);
    }

    private RowMapper<Article> articleRowMapper(){
        return ((rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setTitle(rs.getString("title"));
            article.setHostId(rs.getLong("hostId"));
            article.setCurr(rs.getInt("curr"));
            article.setTotal(rs.getInt("total"));
            article.setClosed(rs.getBoolean("isClosed"));
            article.setContent(rs.getString("content"));
            article.setMeetingDay(rs.getDate("meetingDay"));
            article.setRegionId(rs.getInt("regionId"));
            article.setCategoryId(rs.getInt("categoryId"));
            return article;
        });
    }
}
