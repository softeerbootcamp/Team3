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
import java.util.*;
import java.util.stream.Collectors;

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

        Map<String, Timestamp> meetingDateMap = participatingArticles.stream().collect(
                Collectors.toMap(Article::getUuid, Article::getMeetingDay));

        List<Notification> notifications;

        if(!participatingArticles.isEmpty()) {
            // 구독중인 알림
            notifications = notificationRepository.findByArticleIds(articleUuids);

            notifications.forEach(notification -> notification.setMeetingDay(meetingDateMap.get(notification.getArticleUuid())));

            // 알림 생성일자가 늦은 순 정렬, 만남 일자 빠른 순 정렬
            Collections.sort(notifications);
        } else {
            notifications = new ArrayList<>();
        }

        return new ResponseEntity<>(new CommonResponseDTO<>(1, "구독중인 모든 알림", notifications), HttpStatus.OK);
    }
}
