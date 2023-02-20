package lightning.gathergo.service;

import lightning.gathergo.dto.GatheringDto;
import lightning.gathergo.exception.CustomGlobalException;
import lightning.gathergo.exception.ErrorCode;
import lightning.gathergo.model.Article;
import lightning.gathergo.model.Comment;
import lightning.gathergo.model.User;
import lightning.gathergo.repository.ArticleRepository;
import lightning.gathergo.repository.UserArticleRelationshipRepository;
import lightning.gathergo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.PatternSyntaxException;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentService commentService;
    private final CountService countService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserArticleRelationshipRepository relationshipRepository;


    @Autowired
    ArticleService(ArticleRepository articleRepository, CommentService commentService, CountService countService,
                   UserRepository userRepository, UserArticleRelationshipRepository relationshipRepository, UserService userService){
        this.articleRepository = articleRepository;
        this.commentService = commentService;
        this.relationshipRepository = relationshipRepository;
        this.countService = countService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public Article addArticle(Article article){
        article.setUuid(generateUuid());
        articleRepository.save(article);
        relationshipRepository.save(article.getHostId(), article.getId());
        return articleRepository.findById(articleRepository.getLastInsertedId()).get();
    }

    public List<Article> getArticlesByRegionAndCategoryAndKeyword(Integer regionId, Integer categoryId, String keyword){
        if(regionId < 0 || categoryId < 0 || regionId > 17 || categoryId > 19)
            throw new CustomGlobalException(ErrorCode.BAD_REQUEST);

        if(keyword.equals("")){
            return getArticlesByRegionAndCategory(regionId, categoryId);
        }
        return searchArticlesByKeyword(regionId, categoryId, keyword);
    }

    private List<Article> getArticlesByRegionAndCategory(Integer regionId, Integer categoryId){
        if(regionId < 0 || categoryId < 0 || regionId > 17 || categoryId > 19)
            throw new CustomGlobalException(ErrorCode.BAD_REQUEST);

        if(regionId == 0 && categoryId == 0)
            return articleRepository.findAllArticles();
        if(categoryId == 0)
            return articleRepository.findCurrentRegionArticles(regionId);
        if(regionId == 0)
            return articleRepository.findArticlesByCategoryId(categoryId);
        return articleRepository.findArticlesByRegionAndCategory(regionId, categoryId);
    }

    private List<Article> searchArticlesByKeyword(Integer regionId, Integer categoryId, String keyword){
        if(regionId == 0 && categoryId == 0)
            return articleRepository.findByKeyword(keyword);
        if(categoryId == 0)
            return articleRepository.findByKeywordAndRegion(keyword, regionId);
        if(regionId == 0)
            return articleRepository.findByKeywordAndCategory(keyword, categoryId);
        return articleRepository.findByKeywordAndRegionAndCategory(keyword, regionId, categoryId);
    }

    public List<Article> getImminentArticles(Integer regionId, Integer categoryId){
        if(regionId == 0 && categoryId == 0)
            return articleRepository.findImminentArticles();
        if(regionId == 0)
            return articleRepository.findImminentArticlesByCategoryId(categoryId);
        if(categoryId == 0)
            return articleRepository.findImminentArticlesByRegionId(regionId);
        return articleRepository.findImminentArticlesByCategoryIdRegionId(categoryId, regionId);
    }

    public Article getArticleByUuid(String uuid){
        try{
            Objects.nonNull(articleRepository.findByUuid(uuid).orElse(null));
            return articleRepository.findByUuid(uuid).get();
        }catch (NullPointerException e){
            throw new CustomGlobalException(ErrorCode.NO_RESOURCE);
        }
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


    public void addGuest(String userId, String articleUuid){
        if(countService.getCount(articleUuid) >=
                articleRepository.findByUuid(articleUuid).get().getTotal()){
            throw new CustomGlobalException(ErrorCode.ALREADY_FULLED);
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
    public void mergeLocation(GatheringDto.UpdateRequest request){
        request.setLocation(request.getLocation() + "-=-=-=-=-=" + request.getLocationDetail());
    }
    public void splitLocation(GatheringDto.ArticleDetailResponse data){
        GatheringDto.ArticleFullDto article = data.getArticle();
        String location = article.getLocation();

        String token[] = location.split("-=-=-=-=-=");
        article.setLocation(token[0]);
        article.setLocationDetail(token[1]);
    }

    public void setHasJoinedAndIsHost(GatheringDto.ArticleDetailResponse data, String sessionUserId){
        String hostId = data.getHost().getHostId();
        Integer id = userRepository.findUserByUserId(sessionUserId).get().getId();

        if(hostId.equals(sessionUserId)){
            data.getArticle().setHasJoined(true);
            data.getArticle().setIsHost(true);
        }
        if(relationshipRepository.findGuestsIdByArticleUuid(data.getArticle().getUuid()).contains(id))
            data.getArticle().setHasJoined(true);
    }
    private String generateUuid() {
        return UUID.randomUUID().toString();
    }

    public void validationCheckOn(GatheringDto.CreateRequest request){
        // 요청으로 넘어온 모임 시간이 현재시간 이전일 경우
        if(request.getMeetingDay().before(new Timestamp(System.currentTimeMillis())))
            throw new CustomGlobalException(ErrorCode.INVALID_DATE);
        // 유효하지 않은 모임 인원일 경우
        if(request.getTotal() <= 1)
            throw new CustomGlobalException(ErrorCode.INVALID_TOTAL);
    }
    public void validationCheckOn(GatheringDto.UpdateRequest request){
        // 요청으로 넘어온 모임 시간이 현재시간 이전일 경우
        if(request.getMeetingDay().before(new Timestamp(System.currentTimeMillis())))
            throw new CustomGlobalException(ErrorCode.INVALID_DATE);
        // 유효하지 않은 모임 인원일 경우
        if(request.getTotal() <= 1)
            throw new CustomGlobalException(ErrorCode.INVALID_TOTAL);
    }
}
