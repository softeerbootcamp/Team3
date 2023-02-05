package lightning.gathergo.controller;

import lightning.gathergo.dto.ArticleDto;
import lightning.gathergo.mapper.ArticleMapper;
import lightning.gathergo.model.Article;
import lightning.gathergo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    ResponseEntity<Map<String, Object>> getArticles(@RequestParam Map<String, Integer> requestParams){
        if(requestParams.isEmpty()){
            // throw error or get current region
        }
        Integer regionId = requestParams.get("region");
        Integer categoryId = requestParams.get("category");

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

    ResponseEntity<Article> postArticle(@RequestParam Map<String, String> requestParams){
        return null;
    }
}
