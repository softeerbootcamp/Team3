package lightning.gathergo.controller;

import lightning.gathergo.dto.CommonResponseDTO;
import lightning.gathergo.dto.UserDto;
import lightning.gathergo.exception.ErrorCode;
import lightning.gathergo.exception.ErrorResponse;
import lightning.gathergo.model.Article;
import lightning.gathergo.model.Session;
import lightning.gathergo.model.User;
import lightning.gathergo.service.SessionService;
import lightning.gathergo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<CommonResponseDTO<?>> getUserProfile(@CookieValue String sessionId) {  // 프로필 페이지
        logger.debug("getUserById::SessionId: {}", sessionId);

        Optional<Session> session = sessionService.findSessionBySID(sessionId);

        if(session.isPresent()) {  // 로그인 유효, 로그인 정보 존재
            Optional<User> foundUser = userService.findUserByUserUuid(session.get().getUserUuid());

            // 참여한 모임
            List<Article> participating = userService.getParticipatingArticlesById(foundUser.get().getId());
            logger.debug("participating {}", participating.toString());

            // 호스팅 하는 모임
            List<Article> hosting = userService.getHostingArticlesById(foundUser.get().getId());
            logger.debug("hosting {}", hosting.toString());

            UserDto.GetProfileResponse profileResponse = new UserDto.GetProfileResponse(foundUser.get(), participating, hosting);

            return ResponseEntity.ok().body(
                    new CommonResponseDTO<UserDto.GetProfileResponse>(1, "유저 조회 성공", profileResponse)
            );
        }
        // 로그인 정보가 존재하지 않거나 유저 정보가 없으면
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.NO_RESOURCE);
        return ResponseEntity.ok().body(
                new CommonResponseDTO<ErrorResponse>(0, "유저 조회 실패", errorResponse)
        );

    }

}
