package lightning.gathergo.scheduler;


import lightning.gathergo.model.Article;
import lightning.gathergo.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class ArticleScheduler {
    private final Logger logger = LoggerFactory.getLogger(ArticleScheduler.class);

    private ArticleRepository articleRepository;
    private Timestamp currTimestamp;
    private LocalDateTime now;


    @Autowired
    public ArticleScheduler(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Scheduled(fixedRate = 1000 * 60)
    public void closeArticles(){
        now = LocalDateTime.now();
        now = now.truncatedTo(ChronoUnit.MINUTES);
        logger.debug("close scheduler was called : "+now);

        currTimestamp = Timestamp.valueOf(now);
        List<Article> closableArticles = articleRepository.findClosableArticles(currTimestamp);
        if(closableArticles.size()==0) return;

        closableArticles.forEach(article -> {
            articleRepository.updateArticleById(article.getTitle(), article.getTotal(), true, article.getContent(),
                    article.getMeetingDay(), article.getLocation(), article.getRegionId(), article.getCategoryId(),
                    article.getId());
            logger.debug("article whose uuid is "+article.getUuid()+" was closed");
        });
    }
}
