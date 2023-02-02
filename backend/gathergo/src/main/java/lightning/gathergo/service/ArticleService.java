package lightning.gathergo.service;

import lightning.gathergo.model.Article;
import lightning.gathergo.repository.JdbcArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    private JdbcArticleRepository repo;

    public byte[] convertMultipartFileToByte(MultipartFile multipartFile) throws IOException {
        return multipartFile.getBytes();
    }

    public Article save(Article article){
        article.setUuid(generateUuid());
        //uuid, title, imgPath, curr, total, isClosed, content, meetingDay
        repo.save(article.getUuid(), article.getTitle(), article.getImgPath(), article.getCurr(), article.getTotal(), article.getClosed(),
                article.getContent(), article.getMeetingDay());
        return repo.findById(repo.getLastInsertedId()).get();
    }

    public List<Article> getCurrentRegionArticles(String regionName){
        return repo.findCurrentRegionArticles(regionName);
    }

    public List<Article> getCurrentCategoryArticles(String category){
        return repo.findArticlesByCategory(category);
    }

    public Article setClosedOrNot(Long id, Boolean bool){
        Article article = repo.findById(id).get();
        repo.updateArticleById(article.getTitle(), article.getImgPath(), article.getCurr(),
                article.getTotal(), bool, article.getContent(), article.getMeetingDay(), id);
        article.setClosed(bool);
        return article;
    }

    public Article updateArticle(Long id, Article replacemnt){
        replacemnt.setId(id);
        repo.updateArticleById(replacemnt.getTitle(), replacemnt.getImgPath(), replacemnt.getCurr(),
                replacemnt.getTotal(), replacemnt.getClosed(), replacemnt.getContent(), replacemnt.getMeetingDay(), id);
        return replacemnt;
    }

    // TODO : MultipartFile로 넘어온 사진을 File로 변환해주는 메서드

    // TODO : 사진의 uuid 만드는 메서드(사진명과 유저id의 조합을 해쉬하면 어떨까 생각중) 만든 후 사진 이름을 uuid로 변경

    // TODO : 서버에 사진을 저장하는 메서드

    // TODO : 저장된 사진 경로명으로 article.setImgPath()을 해주는 메서드

    // TODO : 경로명을 다시 사진 파일로 변환해주는 메서드(프론트에게 보내기 위하여)


    // TODO : uuid gen - insert 관련 메서드 수정하기
    private String generateUuid() {
        return UUID.randomUUID().toString();
    }
}
