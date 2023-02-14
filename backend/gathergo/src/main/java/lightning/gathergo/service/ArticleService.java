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
        articleRepository.save(article);
        return articleRepository.findById(articleRepository.getLastInsertedId()).get();
    }

    public List<Article> getArticlesByRegionAndCategoryAndKeyword(Integer regionId, Integer categoryId, String keyword){
        if(keyword.equals("")){
            return getArticlesByRegionAndCategory(regionId, categoryId);
        }
        return searchArticlesByKeyword(regionId, categoryId, keyword);
    }

    public List<Article> getArticlesByRegionAndCategory(Integer regionId, Integer categoryId){
        if(regionId == 0 && categoryId == 0)
            return (List<Article>) articleRepository.findAll();
        if(categoryId == 0)
            return getArticlesByRegion(regionId);
        return articleRepository.findArticlesByRegionAndCategory(regionId, categoryId);
    }

    public List<Article> getArticlesByRegion(Integer regionId){
        return articleRepository.findCurrentRegionArticles(regionId);
    }

    private List<Article> searchArticlesByKeyword(Integer regionId, Integer categoryId, String keyword){
        if(regionId == 0 && categoryId == 0)
            return articleRepository.findByKeyword(keyword);
        if(categoryId == 0)
            return articleRepository.findByKeywordAndRegion(keyword, regionId);
        return articleRepository.findByKeywordAndRegionAndCategory(keyword, regionId, categoryId);
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
    public List<Comment> getCommentsByUuid(String articleUuid){
        return articleRepository.findCommentsByArticleUuid(articleUuid);
    }

    // 댓글 생성
    public Comment addComment(Comment comment, String articleUuid){
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

    public User getUserInfoByFromArticle(String articleUuid){
        return articleRepository.findUserInfoByArticleHostId(articleUuid);
    }

    public void mergeLocation(GatheringDto.CreateRequest request){
        request.setLocation(request.getLocation() + "-=-=-=-=-=" + request.getLocationDetail());
    }
    public void splitLocation(GatheringDto.ArticleDetailResponse data){
        GatheringDto.ArticleFullDto article = data.getArticle();
        String location = article.getLocation();

        String token[] = location.split("-=-=-=-=-=");
        article.setLocation(token[0]);
        article.setLocationDetail(token[1]);
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }
}
