package lightning.gathergo.controller;

import lightning.gathergo.dto.ArticleDto;
import lightning.gathergo.dto.CommentDto;
import lightning.gathergo.mapper.ArticleMapper;
import lightning.gathergo.mapper.CommentMapper;
import lightning.gathergo.model.Article;
import lightning.gathergo.model.Comment;
import lightning.gathergo.model.Session;
import lightning.gathergo.service.ArticleService;
import lightning.gathergo.service.RegionService;
import lightning.gathergo.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RequestMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ArticleController {
    private final ArticleService articleService;
    private final RegionService regionService;
    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;
    private final SessionService sessionService;
    @Autowired
    ArticleController(ArticleService articleService, RegionService regionService, ArticleMapper articleMapper,
                      CommentMapper commentMapper, SessionService sessionService) {
        this.articleService = articleService;
        this.regionService = regionService;
        this.articleMapper = articleMapper;
        this.commentMapper = commentMapper;
        this.sessionService = sessionService;
    }

    // 게시물 리스트
    @GetMapping
    ResponseEntity<Map<String, Object>> getArticles(@RequestParam Map<String, String> requestParams){
        if(requestParams.isEmpty()){
            // throw error or get current region
        }
        Integer regionId = Integer.parseInt(requestParams.get("regionId"));
        Integer categoryId = new Integer(-1);
        try{
            categoryId = Integer.parseInt(requestParams.get("categoryId"));
        } catch (NullPointerException e) {}

        List<ArticleDto.Response> articleDtoList = new ArrayList<>();

        if(null == categoryId)
            articleDtoList = articleMapper.toArticleResponseList(articleService.getCurrentRegionArticles(regionId));
        if(null != categoryId)
            articleDtoList = articleMapper.toArticleResponseList(articleService.getArticlesByRegionAndCategory(regionId, categoryId));

        Map<String, Object> response = new HashMap<>();
        response.put("articles", articleDtoList);
        return ResponseEntity.ok().body(response);
    }

    // 게시물 작성
    @PostMapping
    ResponseEntity<?> createArticle(@RequestBody ArticleDto.CreateRequest request, @CookieValue(name = "sessionId") String sessionId){
        Article article = articleMapper.toArticle(request);
        // TODO service로 아래 파싱 부분 빼기
        String[] locationToken = request.getLocation().split(" ");
        article.setRegionId(regionService.getRegionByName(locationToken[0]).get().getId());

        Session session = sessionService.findSessionBySID(sessionId).get();
        String userId = session.getUserId();

        Map<String, Object> response = new HashMap<>();

        article = articleService.addArticle(article, userId);
        response.put("article", articleMapper.toArticleResponse(article));
        response.put("comments", commentMapper.toCommentResponseList(articleService.getCommentsByUuid(article.getUuid())));
        return ResponseEntity.ok().body(response);
    }

    // 게시물 상세조회
    @GetMapping("/{articleUuid}")
    ResponseEntity<?> getArticle(@PathVariable String articleUuid){
        // 게시물 디비에서 얻어오기
        Article article = articleService.getArticleByUuid(articleUuid);
        Map<String, Object> response = new HashMap<>();

        response.put("article", articleMapper.toArticleResponse(article));
        response.put("comments", commentMapper.toCommentResponseList(articleService.getCommentsByUuid(article.getUuid())));
        return ResponseEntity.ok().body(response);
    }

    // 게시물 수정
    @PutMapping("/{articleUuid}")
    ResponseEntity<?> updateArticle(@PathVariable String articleUuid, @RequestBody ArticleDto.UpdateRequest request){
        Article replacement = articleMapper.toArticle(request);
        String[] locationToken = request.getLocation().split(" ");
        replacement.setRegionId(regionService.getRegionByName(locationToken[0]).get().getId());
        replacement.setUuid(articleUuid);

        Article replaced = articleService.updateArticle(articleUuid, replacement);
        Map<String, Object> response = new HashMap<>();

        response.put("article", articleMapper.toArticleResponse(replaced));
        response.put("comments", commentMapper.toCommentResponseList(articleService.getCommentsByUuid(articleUuid)));

        return ResponseEntity.ok()
                .body(response);
    }

    // 게시물 닫기(soft delete)
    @PutMapping("/{articleUuid}/close")
    ResponseEntity<?> closeArticle(@PathVariable String articleUuid){
        Article closed = articleService.setClosed(articleUuid);
        Map<String, Object> response = new HashMap<>();

        response.put("article", articleMapper.toArticleResponse(closed));
        response.put("comments", commentMapper.toCommentResponseList(articleService.getCommentsByUuid(articleUuid)));

        return ResponseEntity.ok()
                .body(response);
    }

    // 댓글 생성
    @PostMapping("/{articleUuid}/comments")
    ResponseEntity<?> createComment(@PathVariable String articleUuid, @RequestBody CommentDto.CreateRequest request,
                                    @CookieValue(name = "sessionId") String sessionId){
        Map<String, Object> response = new HashMap<>();
        Comment comment = commentMapper.toComment(request);
        Session session = sessionService.findSessionBySID(sessionId).get();
        String userId = session.getUserId();

        articleService.addComment(comment, articleUuid, userId);

        response.put("article", articleMapper.toArticleResponse(articleService.getArticleByUuid(articleUuid)));
        response.put("comments", commentMapper.toCommentResponseList(articleService.getCommentsByUuid(articleUuid)));

        return ResponseEntity.ok()
                .body(response);
    }

    // 댓글 수정
    @PutMapping("/{articleUuid}/comments/{commentUuid}")
    ResponseEntity<?> updateComment(@PathVariable("articleUuid") String articleUuid, @PathVariable("commentUuid") String commentUuid,
                                    @RequestBody CommentDto.UpdateReqeust request, @CookieValue(name = "sessionId") String sessionId){
        Map<String, Object> response = new HashMap<>();
        Session session = sessionService.findSessionBySID(sessionId).get();
        String userId = session.getUserId();

        if(!articleService.hasUserWroteThisComment(userId, commentUuid)){
            // throw exception
            // 수정 요청한 유저가 해당 댓글을 쓰지 않았을 경우
        }

        Comment replacement = commentMapper.toComment(request);
        replacement.setUuid(commentUuid);
        articleService.updateComment(replacement);

        response.put("article", articleMapper.toArticleResponse(articleService.getArticleByUuid(articleUuid)));
        response.put("comments", commentMapper.toCommentResponseList(articleService.getCommentsByUuid(articleUuid)));

        return ResponseEntity.ok()
                .body(response);
    }

    // 댓글 삭제
    @DeleteMapping("/{articleUuid}/comments/{commentUuid}")
    ResponseEntity<?> deleteComment(@PathVariable("articleUuid") String articleUuid, @PathVariable("commentUuid") String commentUuid,
                                    @CookieValue(name = "sessionId") String sessionId){
        Map<String, Object> response = new HashMap<>();
        Session session = sessionService.findSessionBySID(sessionId).get();
        String userId = session.getUserId();

        if(!articleService.hasUserWroteThisComment(userId, commentUuid)){
            // throw exception
            // 수정 요청한 유저가 해당 댓글을 쓰지 않았을 경우
        }

        articleService.deleteComment(commentUuid);

        response.put("article", articleMapper.toArticleResponse(articleService.getArticleByUuid(articleUuid)));
        response.put("comments", commentMapper.toCommentResponseList(articleService.getCommentsByUuid(articleUuid)));

        return ResponseEntity.ok()
                .body(response);
    }

    // 참가
    @PutMapping("/{articleUuid}/users")
    ResponseEntity<?> joinArticle(@PathVariable("articleUuid") String articleUuid, @CookieValue(name = "sessionId") String sessionId){
        Session session = sessionService.findSessionBySID(sessionId).get();
        String userId = session.getUserId();

        Map<String, Object> response = new HashMap<>();
//        if(articleService.judgeJoinedUserOrNot(userId, articleUuid)){여
//            response.put("message", "이미 참가한 모임입니다. 모임에서 나가시겠습니까?");
//            return ResponseEntity.status(400)
//                    .body(response);
//        }

        articleService.addGuest(userId, articleUuid);

        response.put("article", articleMapper.toArticleResponse(articleService.getArticleByUuid(articleUuid)));
        response.put("comments", commentMapper.toCommentResponseList(articleService.getCommentsByUuid(articleUuid)));

        return ResponseEntity.ok()
                .body(response);
    }

    // 나가기
    @DeleteMapping("/{articleUuid}/users")
    ResponseEntity<?> exitArticle(@PathVariable("articleUuid") String articleUuid, @CookieValue(name = "sessionId") String sessionId){
        Session session = sessionService.findSessionBySID(sessionId).get();
        String userId = session.getUserId();
        Map<String, Object> response = new HashMap<>();
        articleService.deleteGuest(userId, articleUuid);

        response.put("article", articleMapper.toArticleResponse(articleService.getArticleByUuid(articleUuid)));
        response.put("comments", commentMapper.toCommentResponseList(articleService.getCommentsByUuid(articleUuid)));

        return ResponseEntity.ok()
                .body(response);
    }

}
