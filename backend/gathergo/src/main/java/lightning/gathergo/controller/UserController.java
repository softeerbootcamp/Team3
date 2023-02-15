package lightning.gathergo.controller;

import lightning.gathergo.dto.CommonResponseDTO;
import lightning.gathergo.dto.UserDto;
import lightning.gathergo.exception.ErrorCode;
import lightning.gathergo.exception.ErrorResponse;
import lightning.gathergo.model.Session;
import lightning.gathergo.model.User;
import lightning.gathergo.service.CookieService;
import lightning.gathergo.service.SessionService;
import lightning.gathergo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public UserController(UserService userService, SessionService sessionService, CookieService cookieService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<?> getUserById(@CookieValue String sessionId) {
        System.out.println(sessionId);
        //String realSessionId = sessionId.trim().split(";")[0];
        Optional<Session> session = sessionService.findSessionBySID(sessionId);
        if(session.isPresent()){
            //session.get().getUserUuid();
            Optional<User> user = userService.findUserByUserUuid(session.get().getUserUuid());
            // 참여한 모임
            //userService.getParticipatingArticlesByIdd(user.get().getId());
            //userService.getHostingArticlesById(user.get().getId());

            UserDto.Response response = new UserDto.Response();
            response.setUuid(user.get().getUuid());
            response.setUserId(user.get().getUserId());
            response.setUserName(user.get().getUserName());
            response.setPassword(user.get().getPassword());
            response.setEmail(user.get().getEmail());
            response.setPassword(user.get().getPassword());
            response.setIntroduction(user.get().getIntroduction());
            response.setProfilePath(user.get().getProfilePath());
            response.setArticleList(userService.getParticipatingArticlesByIdd(user.get().getId()));
            response.setHostingArticleList(userService.getHostingArticlesById(user.get().getId()));

            return ResponseEntity.ok().body(
                    new CommonResponseDTO<UserDto.Response>(1,"유저 조회 성공",response)
            );

        }
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.NO_RESOURCE);
        return ResponseEntity.ok().body(
                new CommonResponseDTO<ErrorResponse>(0,"유저 조회 실패",errorResponse)
        );

    }

}
