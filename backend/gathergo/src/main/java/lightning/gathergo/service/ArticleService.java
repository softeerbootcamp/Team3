package lightning.gathergo.service;

import lightning.gathergo.model.Article;
import lightning.gathergo.model.Comment;
import lightning.gathergo.repository.ArticleRepository;
import lightning.gathergo.repository.UserArticleRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    // TODO hostId가 string 값으로 날라오도록 변경
    private final ArticleRepository articleRepository;
    private final CommentService commentService;

    private final UserArticleRelationshipRepository relationshipRepository;

    @Autowired
    ArticleService(ArticleRepository articleRepository, CommentService commentService, UserArticleRelationshipRepository relationshipRepository){
        this.articleRepository = articleRepository;
        this.commentService = commentService;
        this.relationshipRepository = relationshipRepository;
    }

    @Transactional
    public Article addArticle(Article article){
        article.setUuid(generateUuid());
        System.out.println(article.getTitle());
        articleRepository.save(article);
        return articleRepository.findById(articleRepository.getLastInsertedId()).get();
    }

    @Transactional
    public List<Article> getCurrentRegionArticles(Integer regionId){
        return articleRepository.findCurrentRegionArticles(regionId);
    }

    public List<Article> getArticlesByRegionAndCategory(Integer regionId, Integer categoryId){
        return articleRepository.findArticlesByRegionAndCategory(regionId, categoryId);
    }

    public Article getArticleByUuid(String uuid){
        return articleRepository.findByUuid(uuid).get();
    }

    public Article setClosed(String uuid){
        Article article = articleRepository.findByUuid(uuid).get();
        articleRepository.updateArticleById(article.getTitle(), article.getCurr(),
                article.getTotal(), true, article.getContent(), article.getMeetingDay(), article.getLocation(),
                article.getRegionId(), article.getCategoryId(), article.getId());
        article.setClosed(true);
        return article;
    }

    public Article updateArticle(String uuid, Article replacemnt){
        Integer id = articleRepository.findByUuid(uuid).get().getId();
        replacemnt.setId(id);
        articleRepository.updateArticleById(replacemnt.getTitle(), replacemnt.getCurr(),
                replacemnt.getTotal(), replacemnt.getClosed(), replacemnt.getContent(), replacemnt.getMeetingDay(),
                replacemnt.getLocation(), replacemnt.getRegionId(), replacemnt.getCategoryId(), id);
        return replacemnt;
    }

    public List<Comment> getCommentsByUuid(String articleUuid){
        return articleRepository.findCommentsByArticleId(articleUuid);
    }

    // 댓글 생성
    public Comment addComment(Comment comment){
        return commentService.createComment(comment);
    }

    // 댓글 리스트 반환
    public List<Comment> getCommentsByArticleUuid(String articleUuid){
        return articleRepository.findCommentsByArticleId(articleUuid);
    }

    // 댓글 수정
    public Comment updateComment(Comment comment){
        return commentService.updateComment(comment.getContent(), comment.getDate(), comment.getUuid());
    }

    // 댓글 삭제
    public void deleteComment(String commentUuid){
        commentService.deleteCommentByUuid(commentUuid);
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }
}
