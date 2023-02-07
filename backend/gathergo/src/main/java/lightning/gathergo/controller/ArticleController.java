package lightning.gathergo.controller;

import lightning.gathergo.dto.ArticleDto;
import lightning.gathergo.mapper.ArticleMapper;
import lightning.gathergo.model.Article;
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

    @Autowired
    ArticleController(ArticleService articleService, ArticleMapper articleMapper) {
        this.articleService = articleService;
        this.articleMapper = articleMapper;
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
        Article article = articleService.getArticleByUuid(articleUuid);
        return new ResponseEntity<ArticleDto.Response>(articleMapper.toArticleResponse(article), HttpStatus.FOUND);
    }

    @PutMapping("/{articleUuid}")
    ResponseEntity<ArticleDto.Response> updateArticle(@PathVariable String articleUuid, @RequestBody ArticleDto.UpdateRequest request){
        Article replacement = articleMapper.toArticle(request);
        Article replaced = articleService.updateArticle(articleUuid, replacement);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/articles/"+articleUuid);

        return ResponseEntity.ok()
                .headers(headers)
                .body(articleMapper.toArticleResponse(replaced));
    }

    @PutMapping("/{articleUuid}/close")
    ResponseEntity<ArticleDto.Response> closeArticle(@PathVariable String articleUuid){
        Article closed = articleService.setClosed(articleUuid);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "index.html");

        return ResponseEntity.ok()
                .headers(headers)
                .body(articleMapper.toArticleResponse(closed));
    }
}
