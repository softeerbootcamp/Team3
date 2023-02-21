package lightning.gathergo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Table("notification")
public class Notification implements Comparable<Notification> {
    @Id
    private Integer id;

    @Column("articleUuid")
    private String articleUuid;
    @Column("title")
    private String title;
    @Column("body")
    private String body;
    @Column("issueDateTime")
    @JsonSerialize(as = Timestamp.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp issueDateTime;

    @JsonSerialize(as = Timestamp.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp meetingDay;

    public Notification(Integer id, String articleUuid, String title, String body, Timestamp issueDateTime) {
        this.id = id;
        this.articleUuid = articleUuid;
        this.title = title;
        this.body = body;
        this.issueDateTime = issueDateTime;
    }

    // Repository 조회용
    public Notification(String articleUuid, String title, String body, Timestamp issueDateTime) {
        this.articleUuid = articleUuid;
        this.title = title;
        this.body = body;
        this.issueDateTime = issueDateTime;
    }

    public Notification() {
    }

    public String getArticleUuid() {
        return articleUuid;
    }

    public void setArticleUuid(String articleUuid) {
        this.articleUuid = articleUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getIssueDateTime() {
        return issueDateTime;
    }

    public void setIssueDateTime(Timestamp issueDateTime) {
        this.issueDateTime = issueDateTime;
    }

    public Timestamp getMeetingDay() {
        return meetingDay;
    }

    public void setMeetingDay(Timestamp meetingDay) {
        this.meetingDay = meetingDay;
    }



    @Override
    public int compareTo(Notification n) {
        int result = this.issueDateTime.compareTo(n.issueDateTime);
        return (result != 0) ? -result : this.meetingDay.compareTo(n.meetingDay);
    }
}
