package lightning.gathergo.service;

import lightning.gathergo.model.Article;
import lightning.gathergo.repository.JdbcArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@Service
public class ArticleService {

    private JdbcArticleRepository repo;

    public byte[] convertMultipartFileToByte(MultipartFile multipartFile) throws IOException {
        return multipartFile.getBytes();
    }

    public Article save(Article article){
        repo.save(article);
        return repo.findById(repo.getLastInsertedId()).get();
    }

    public List<Article> getCurrentRegionArticles(String regionName){
        return repo.findCurrentRegionArticles(regionName);
    }

    public List<Article> getCurrentCategoryArticles(String category){
        return repo.findArticlesByCategory(category);
    }
}
