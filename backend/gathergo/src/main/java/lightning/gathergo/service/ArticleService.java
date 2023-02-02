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

    public byte[] convertMultipartFileToByte(MultipartFile multipartFile) throws IOException {
        return multipartFile.getBytes();
    }

    public Article save(Article article){
        article.setUuid(generateUuid());

        repo.save(article);
        return repo.findById(repo.getLastInsertedId()).get();
    }

    public List<Article> getCurrentRegionArticles(String regionName){
        //return repo.findCurrentRegionArticles(regionName);
        return null;
    }

    public List<Article> getCurrentCategoryArticles(String category){
        //return repo.findArticlesByCategory(category);
        return null;
    }

    public Article setClosedOrNot(Long id, Boolean bool){
        Article article = repo.findById(id).get();
        repo.updateArticleById(article.getTitle(), article.getThumbnail(), article.getCurr(),
                article.getTotal(), bool, article.getContent(), article.getMeetingDay(), article.getLocation(),
                article.getRegionId(), article.getCategoryId(), article.getId());
        article.setClosed(bool);
        return article;
    }

    public Article updateArticle(Long id, Article replacemnt){
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
