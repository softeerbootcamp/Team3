package lightning.gathergo.controller;

import lightning.gathergo.dto.ArticleDto;
import lightning.gathergo.dto.CommentDto;
import lightning.gathergo.mapper.ArticleMapper;
import lightning.gathergo.mapper.CommentMapper;
import lightning.gathergo.model.Article;
import lightning.gathergo.model.Comment;
import lightning.gathergo.service.ArticleService;
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
    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;

    @Autowired
    ArticleController(ArticleService articleService, ArticleMapper articleMapper, CommentMapper commentMapper) {
        this.articleService = articleService;
        this.articleMapper = articleMapper;
        this.commentMapper = commentMapper;
    }

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
        response.put("count", articleDtoList.size());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    ResponseEntity<ArticleDto.Response> addArticle(@RequestBody ArticleDto.CreateRequest request){
        Article article = articleMapper.toArticle(request);

        article = articleService.addArticle(article);
        return ResponseEntity.ok().body(articleMapper.toArticleResponse(article));
    }

    @GetMapping("/{articleUuid}")
    ResponseEntity<ArticleDto.Response> getArticle(@PathVariable String articleUuid){
        // 게시물 디비에서 얻어오기
        Article article = articleService.getArticleByUuid(articleUuid);
        ArticleDto.Response result = articleMapper.toArticleResponse(article);
        // 게시물에 달린 댓글 디비에서 얻어오기
        result.setComments(commentMapper.toCommentResponseList(articleService.getCommentsByUuid(articleUuid)));
        return new ResponseEntity<ArticleDto.Response>(result, HttpStatus.FOUND);
    }

    @PutMapping("/{articleUuid}")
    ResponseEntity<ArticleDto.Response> updateArticle(@PathVariable String articleUuid, @RequestBody ArticleDto.UpdateRequest request){
        Article replacement = articleMapper.toArticle(request);
        Article replaced = articleService.updateArticle(articleUuid, replacement);
        ArticleDto.Response result = articleMapper.toArticleResponse(replaced);

        // 게시물에 달린 댓글 디비에서 얻어오기
        result.setComments(commentMapper.toCommentResponseList(articleService.getCommentsByUuid(articleUuid)));
        HttpHeaders headers = new HttpHeaders();

        return ResponseEntity.ok()
                .body(result);
    }

    @PutMapping("/{articleUuid}/close")
    ResponseEntity<ArticleDto.Response> closeArticle(@PathVariable String articleUuid){
        Article closed = articleService.setClosed(articleUuid);
        HttpHeaders headers = new HttpHeaders();

        return ResponseEntity.ok()
                .body(articleMapper.toArticleResponse(closed));
    }

    // 댓글 생성
    @PostMapping("/{articleUuid}/comments/{commentUuid}")
    ResponseEntity<ArticleDto.Response> addComment(@PathVariable("articleUuid") String articleUuid, @RequestBody CommentDto.CreateRequest request){
        Comment comment = commentMapper.toComment(request);
        articleService.addComment(comment);

        ArticleDto.Response result = articleMapper.toArticleResponse(articleService.getArticleByUuid(articleUuid));
        result.setComments(commentMapper.toCommentResponseList(articleService.getCommentsByUuid(articleUuid)));

        return ResponseEntity.ok()
                .body(result);
    }

    // 댓글 수정
    @PutMapping("/{articleUuid}/comments/{commentUuid}")
    ResponseEntity<ArticleDto.Response> updateComment(@PathVariable("articleUuid") String articleUuid, @PathVariable("commentUuid") String commentUuid, @RequestBody CommentDto.UpdateReqeust request){
        Comment replacement = commentMapper.toComment(request);
        replacement.setUuid(commentUuid);
        articleService.updateComment(replacement);

        ArticleDto.Response result = articleMapper.toArticleResponse(articleService.getArticleByUuid(articleUuid));
        result.setComments(commentMapper.toCommentResponseList(articleService.getCommentsByUuid(articleUuid)));

        return ResponseEntity.ok()
                .body(result);
    }

    // 댓글 삭제
    @DeleteMapping("/{articleUuid}/comments/{commentUuid}")
    ResponseEntity<ArticleDto.Response> deleteComment(@PathVariable("articleUuid") String articleUuid, @PathVariable("commentUuid") String commentUuid, @RequestBody CommentDto.DeleteRequest request){
        articleService.deleteComment(commentUuid);

        ArticleDto.Response result = articleMapper.toArticleResponse(articleService.getArticleByUuid(articleUuid));
        result.setComments(commentMapper.toCommentResponseList(articleService.getCommentsByUuid(articleUuid)));

        return ResponseEntity.ok()
                .body(result);
    }
}
