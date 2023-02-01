package lightning.gathergo.repository;

import lightning.gathergo.model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface JdbcArticleRepository extends CrudRepository<Article, Long> {

    @Modifying
    @Query("INSERT INTO article (title, imgPath, curr, total, isClosed, content, meetingDay) " +
            " values (:title, :imgPath, :curr, :total, :isClosed, :content, :meetingDay)")
    public void save(String title, String imgPath, int curr,
                         int total, boolean isClosed, String content, Date meetingDay);

    @Override
    @Query("SELECT * FROM article WHERE id = :id")
    default Optional<Article> findById(Long id) {
        return Optional.empty();
    }
}
