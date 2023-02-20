package lightning.gathergo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

public class Notification {
    @Id
    private Integer id;
    private String articleUuid;
    private String title;
    private String body;
    @JsonSerialize(as = Timestamp.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp issueDateTime;

    // Repository 조회용
    public Notification(Integer id, String articleUuid, String title, String body, Timestamp issueDateTime) {
        this.id = id;
        this.articleUuid = articleUuid;
        this.title = title;
        this.body = body;
        this.issueDateTime = issueDateTime;
    }

    public Notification(Integer id, String articleUuid, String title, String body) {
        this.id = id;
        this.articleUuid = articleUuid;
        this.title = title;
        this.body = body;
    }
}
