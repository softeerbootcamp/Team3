package lightning.gathergo.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lightning.gathergo.dto.ArticleDto;
import lightning.gathergo.dto.CommentDto;
import lightning.gathergo.mapper.ArticleMapper;
import lightning.gathergo.mapper.CommentMapper;
import lightning.gathergo.repository.UserRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserIdPrePostAdvisor {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;

    @Autowired
    public UserIdPrePostAdvisor(UserRepository userRepository,
                                ArticleMapper articleMapper, CommentMapper commentMapper){
        this.userRepository = userRepository;
        this.objectMapper = new ObjectMapper();
        this.articleMapper = articleMapper;
        this.commentMapper = commentMapper;
    }

    @Pointcut("execution(* lightning.gathergo.controller.ArticleController.*(..))")
    private void afterReturingAdvisor(){}

    @AfterReturning(value = "afterReturingAdvisor()", returning = "returnValue")
    public void afterReturn(JoinPoint joinPoint, ResponseEntity returnValue) throws IOException {
        if (joinPoint.getSignature().getName().startsWith("join") || joinPoint.getSignature().getName().startsWith("exit")) return;
        // 모든 게시물 조회일 경우
        if (joinPoint.getSignature().getName().equals("getArticles")){
            convertGetArticlesJson(returnValue);
            return;
        }

        Map<String, Object> body = (Map<String, Object>) returnValue.getBody();
        ArticleDto.Response article = (ArticleDto.Response) body.get("article");
        List<CommentDto.Response> comments = (List<CommentDto.Response>) body.get("comments");

        article.setHostId(
                userRepository.findById(Integer.parseInt(article.getHostId())).get().getUserId()
        );
        comments.forEach(
                comment->{
                    comment.setUserId(userRepository.findById(Integer.parseInt(comment.getUserId())).get().getUserId());
                }
        );
    }

    private void convertGetArticlesJson(ResponseEntity returnValue){
        Map<String, Object> body = (Map<String, Object>) returnValue.getBody();
        List<ArticleDto.Response> articles = (List<ArticleDto.Response>) body.get("articles");
        articles.forEach(
                article -> {
                    article.setHostId(
                            userRepository.findById(Integer.parseInt(article.getHostId())).get().getUserId()
                    );
                }
        );
    }
}
