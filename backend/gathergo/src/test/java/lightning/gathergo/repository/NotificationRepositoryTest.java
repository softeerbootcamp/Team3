package lightning.gathergo.repository;

import lightning.gathergo.model.Article;
import lightning.gathergo.model.Notification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

// @ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("local")
@Transactional
public class NotificationRepositoryTest {
    @Autowired
    private NotificationRepository notificationRepository;

    private final String DUMMY_UUID = "dd5c1220-841b-4be0-9518-795fc6f4dd0a";

    @MockBean
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("참여중인 게시글 기준으로 등록된 알림 내역 uuid로 찾아오기 테스기")
    void save() {

        // given
        // when(articleRepository.findByUuid(DUMMY_UUID)).thenReturn(Optional.of(new Article(1, "테스트 새 글 1", 2, false, "내용", Timestamp.valueOf("2023-02-21 12:12:12"), "서울시 성동구", 1, 1, DUMMY_UUID)));
        String uuid1 = String.valueOf(UUID.randomUUID());
        String uuid2 = String.valueOf(UUID.randomUUID());

        Article article1 = new Article(1, "테스트 새 글 1", 2, false, "내용1", Timestamp.valueOf("2023-02-21 12:12:12"), "서울시 성동구", 1, 1, uuid1);
        Article article2 = new Article(2, "테스트 새 글 2", 4, false, "내용2", Timestamp.valueOf("2023-02-22 12:12:13"), "서울시 중구", 1, 1, uuid1);

        // 각각 article1, article2에 댓글과 새 글, 특정 유저 참여 가정
        notificationRepository.save(uuid1, article1.getTitle(), "댓글이 등록되었습니다", Timestamp.valueOf("2023-02-21 12:12:12"));
        notificationRepository.save(uuid2, article2.getTitle(), "a님이 참여하셧습니다", Timestamp.valueOf("2023-02-22 12:12:13"));

        // when
        // (게시글 참여 = 구독) 정보 기반으로 uuid 등록된 알림 내역 잘 찾아오는지 쿼리 테스트
        List<Notification> notifications = notificationRepository.findByArticleIds(List.of(uuid1, uuid2));

        notifications.sort(Comparator.comparing(Notification::getIssueDateTime));

        // then
        assertThat(notifications)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(List.of(
                new Notification(uuid1, article1.getTitle(), "댓글이 등록되었습니다", article1.getMeetingDay()),
                new Notification(uuid2, article2.getTitle(), "a님이 참여하셧습니다", article2.getMeetingDay())
        ));
    }
}
