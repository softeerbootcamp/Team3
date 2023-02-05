package lightning.gathergo.service;

import lightning.gathergo.model.Article;
import lightning.gathergo.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    private ArticleRepository repo;

    public Article save(Article article){
        article.setUuid(generateUuid());

        repo.save(article);
        return repo.findById(repo.getLastInsertedId()).get();
    }

    public List<Article> getCurrentRegionArticles(Integer regionId){
        return repo.findCurrentRegionArticles(regionId);
    }

    public List<Article> getArticlesByRegionAndCategory(Integer regionId, Integer categoryId){
        return repo.findArticlesByRegionAndCategory(regionId, categoryId);
    }

    public Article setClosed(String uuid){
        Article article = repo.findByUuid(uuid).get();
        repo.updateArticleById(article.getTitle(), article.getThumbnail(), article.getCurr(),
                article.getTotal(), true, article.getContent(), article.getMeetingDay(), article.getLocation(),
                article.getRegionId(), article.getCategoryId(), article.getId());
        article.setClosed(true);
        return article;
    }

    public Article updateArticle(String uuid, Article replacemnt){
        Long id = repo.findByUuid(uuid).get().getId();
        replacemnt.setId(id);
        repo.updateArticleById(replacemnt.getTitle(), replacemnt.getThumbnail(), replacemnt.getCurr(),
                replacemnt.getTotal(), replacemnt.getClosed(), replacemnt.getContent(), replacemnt.getMeetingDay(),
                replacemnt.getLocation(), replacemnt.getRegionId(), replacemnt.getCategoryId(), id);
        return replacemnt;
    }

    // TODO : 카테고리 정보를 기준으로 article.setImgPath()을 해주는 메서드

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }
}
