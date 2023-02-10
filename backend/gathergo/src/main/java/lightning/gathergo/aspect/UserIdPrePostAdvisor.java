package lightning.gathergo.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lightning.gathergo.dto.ArticleDto;
import lightning.gathergo.dto.CommentDto;
import lightning.gathergo.mapper.ArticleMapper;
import lightning.gathergo.repository.UserRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

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

    @Autowired
    public UserIdPrePostAdvisor(UserRepository userRepository, ArticleMapper articleMapper){
        this.userRepository = userRepository;
        this.objectMapper = new ObjectMapper();
        this.articleMapper = articleMapper;
    }

    @Pointcut("execution(* lightning.gathergo.controller.ArticleController.add*(..))")
    private void stringToIntegerAdvisor(){}

    @Pointcut("execution(* lightning.gathergo.controller.ArticleController.*(..))")
    private void integerToStringAdvisor(){}

    @Before("stringToIntegerAdvisor()")
    public void before(JoinPoint joinPoint) throws IllegalAccessException {
        Object[] params = joinPoint.getArgs();
        for (Object arg : params) {
            if(arg instanceof ArticleDto.CreateRequest){
                ArticleDto.CreateRequest json = new ArticleDto.CreateRequest();
                json.setHostId(userRepository.findUserByUserId(((ArticleDto.CreateRequest) arg).getHostId()).get().getId().toString());
                ((ArticleDto.CreateRequest) arg).setHostId(json.getHostId());
                break;
            }
            if(arg instanceof CommentDto.CreateRequest){
                CommentDto.CreateRequest json = new CommentDto.CreateRequest();
                json.setUserId(userRepository.findUserByUserId(((CommentDto.CreateRequest) arg).getUserId()).get().getId().toString());
                ((CommentDto.CreateRequest) arg).setUserId(json.getUserId());
                break;
            }
        }
    }

    @AfterReturning(value = "integerToStringAdvisor()", returning = "returnValue")
    public void afterReturn(JoinPoint joinPoint, ResponseEntity returnValue){
        if (joinPoint.getSignature().getName().equals("getArticles")){
            convertGetArticlesJson(returnValue);
            return;
        }

        ArticleDto.Response json = (ArticleDto.Response) returnValue.getBody();

        json.setHostId(
                userRepository.findById(Integer.parseInt(json.getHostId()))
                .get().getUserId());
        json.getComments().forEach(
                comment ->{
                    comment.setUserId(userRepository.findById(Integer.parseInt(comment.getUserId())).get().getUserId());
                }
        );

        returnValue = ResponseEntity.ok().body(json);
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
