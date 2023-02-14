package lightning.gathergo.service;

import lightning.gathergo.model.Article;
import lightning.gathergo.model.Comment;
import lightning.gathergo.repository.ArticleRepository;
import lightning.gathergo.repository.UserArticleRelationshipRepository;
import lightning.gathergo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    // TODO hostId가 string 값으로 날라오도록 변경
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final UserArticleRelationshipRepository relationshipRepository;
    private final CommentService commentService;
    private final CountService countService;

    @Autowired
    ArticleService(ArticleRepository articleRepository, UserRepository userRepository,
                   UserArticleRelationshipRepository relationshipRepository, CommentService commentService,
                   CountService countService){
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.relationshipRepository = relationshipRepository;
        this.commentService = commentService;
        this.countService = countService;
    }

    @Transactional
    public Article addArticle(Article article, String userId){
        article.setUuid(generateUuid());
        article.setHostId(userRepository.findUserByUserId(userId).get().getId());
        articleRepository.save(article);
        relationshipRepository.save(
                article.getHostId()
                , article.getHostId());
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
        articleRepository.updateArticleById(article.getTitle(),
                article.getTotal(), true, article.getContent(), article.getMeetingDay(), article.getLocation(),
                article.getRegionId(), article.getCategoryId(), article.getId());
        article.setClosed(true);
        return article;
    }

    public Article updateArticle(String uuid, Article replacemnt){
        Integer id = articleRepository.findByUuid(uuid).get().getId();
        replacemnt.setId(id);
        articleRepository.updateArticleById(replacemnt.getTitle(),
                replacemnt.getTotal(), replacemnt.getClosed(), replacemnt.getContent(), replacemnt.getMeetingDay(),
                replacemnt.getLocation(), replacemnt.getRegionId(), replacemnt.getCategoryId(), id);
        return replacemnt;
    }

    public List<Comment> getCommentsByUuid(String articleUuid){
        return articleRepository.findCommentsByArticleUuid(articleUuid);
    }

    // 댓글 생성
    public Comment addComment(Comment comment, String articleUuid, String userId){
        comment.setUserId(userRepository.findUserByUserId(userId).get().getId());
        comment.setArticleId(articleRepository.findByUuid(articleUuid).get().getId());
        return commentService.createComment(comment);
    }


    // 댓글 수정
    public Comment updateComment(Comment comment){
        return commentService.updateComment(comment.getContent(), comment.getDate(), comment.getUuid());
    }

    // 댓글 삭제
    public void deleteComment(String commentUuid){
        commentService.deleteCommentByUuid(commentUuid);
    }

    public void addGuest(String userId, String articleUuid){
        if(countService.getCount(articleUuid) >=
                articleRepository.findByUuid(articleUuid).get().getTotal()){
            // throw exception
        }
        relationshipRepository.save(
                userRepository.findUserByUserId(userId).get().getId(),
                articleRepository.findByUuid(articleUuid).get().getId()
        );
    }

    public void deleteGuest(String userId, String articleUuid){
        relationshipRepository.deleteByArticleIdUserId(
                userRepository.findUserByUserId(userId).get().getId(),
                articleRepository.findByUuid(articleUuid).get().getId()
        );
    }

    public boolean judgeJoinedUserOrNot(String userId, String articleUuid){
        List<Article> articles = userRepository.findParticipatingArticlesById(userRepository.findUserByUserId(userId).get().getId());
        for(Article article : articles){
            if(article.getUuid().equals(articleUuid)) return true;
        }
        return false;
    }

    // 댓글 작성 여부 확인
    public boolean hasUserWroteThisComment(String userId, String commentUuid){
        return commentService.hasUserWroteThisComment(userId, commentUuid);
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }
}
