//package lightning.gathergo.aspect;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lightning.gathergo.dto.CommonResponseDTO;
//import lightning.gathergo.dto.GatheringDto;
//import lightning.gathergo.dto.CommentDto;
//import lightning.gathergo.mapper.ArticleMapper;
//import lightning.gathergo.repository.UserRepository;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class UserIdPrePostAdvisor {
//    private final UserRepository userRepository;
//    private final ObjectMapper objectMapper;
//    private final ArticleMapper articleMapper;
//
//    @Autowired
//    public UserIdPrePostAdvisor(UserRepository userRepository, ArticleMapper articleMapper){
//        this.userRepository = userRepository;
//        this.objectMapper = new ObjectMapper();
//        this.articleMapper = articleMapper;
//    }
//
//    @Pointcut("execution(* lightning.gathergo.controller.ArticleController.getArticle(..))")
//    private void integerToStringAdvisor(){}
//    @AfterReturning(value = "integerToStringAdvisor()", returning = "returnValue")
//    public void afterReturn(JoinPoint joinPoint, ResponseEntity returnValue){
//        if (joinPoint.getSignature().getName().equals("getArticles")){
//            convertGetArticlesJson(returnValue);
//            return;
//        }
//
//        CommonResponseDTO json = (CommonResponseDTO) returnValue.getBody();
//
//        json.setHostId(
//                userRepository.findById(Integer.parseInt(json.getHostId()))
//                .get().getUserId());
//        json.getComments().forEach(
//                comment ->{
//                    comment.setUserId(userRepository.findById(Integer.parseInt(comment.getUserId())).get().getUserId());
//                }
//        );
//
//        returnValue = ResponseEntity.ok().body(json);
//    }
//
//    private void convertGetArticlesJson(ResponseEntity returnValue){
//        Map<String, Object> body = (Map<String, Object>) returnValue.getBody();
//        List<GatheringDto.Response> articles = (List<GatheringDto.Response>) body.get("articles");
//        articles.forEach(
//                article -> {
//                    article.setHostId(
//                            userRepository.findById(Integer.parseInt(article.getHostId())).get().getUserId()
//                    );
//                }
//        );
//    }
//}
