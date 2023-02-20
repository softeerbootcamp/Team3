package lightning.gathergo.controller;

import lightning.gathergo.dto.CommonResponseDTO;
import lightning.gathergo.model.Article;
import lightning.gathergo.model.Notification;
import lightning.gathergo.model.Session;
import lightning.gathergo.model.UserArticleRelationship;
import lightning.gathergo.repository.NotificationRepository;
import lightning.gathergo.repository.UserArticleRelationshipRepository;
import lightning.gathergo.service.SessionService;
import lightning.gathergo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class NotificationController {
    private final SessionService sessionService;
    private final UserService userService;
    private final NotificationRepository notificationRepository;

    public NotificationController(SessionService sessionService, UserService userService, NotificationRepository notificationRepository) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.notificationRepository = notificationRepository;
    }

    @GetMapping("/notifications")
    public ResponseEntity<CommonResponseDTO<?>> getNotifications(@CookieValue("sessionId") String sessionId) {
        Optional<Session> session = sessionService.findSessionBySID(sessionId);

        if(session.isEmpty())
            return new ResponseEntity<>(new CommonResponseDTO<>(0, "로그인이 안된 사용자입니다", null), HttpStatus.UNAUTHORIZED);

        // 내가 참여(구독하는 게시글 정보)
        List<Article> participatingArticles = userService.getParticipatingArticlesById(session.get().getId());

        List<String> articleUuids = participatingArticles.stream().map(Article::getUuid).collect(Collectors.toList());
        List<Timestamp> meetingDateTime = participatingArticles.stream().map(Article::getMeetingDay).collect(Collectors.toList());

        List<Notification> notifications;

        if(!participatingArticles.isEmpty()) {
            // 구독중인 알림
            notifications = notificationRepository.findByArticleIds(articleUuids);
            notifications.sort(Comparator.comparing(Notification::getIssueDateTime));

            IntStream.range(0, Math.min(notifications.size(), meetingDateTime.size()))
                    .forEach(i -> notifications.get(i).setMeetingDay(meetingDateTime.get(i)));
        } else {
            notifications = new ArrayList<>();
        }

        return new ResponseEntity<>(new CommonResponseDTO<>(1, "구독중인 모든 알림", notifications), HttpStatus.OK);
    }
}
