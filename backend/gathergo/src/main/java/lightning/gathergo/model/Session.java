package lightning.gathergo.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("session")
public class Session {
    protected String sessionId;
    protected final LocalDateTime createDate;
    @Value("${session.duration}")
    protected int duration;
    protected Integer id;
    protected String userId;
    protected String userName;
    protected String userUuid;

    public Session(Integer id, String userId, String userName) {
        this.id = id;
        this.sessionId = String.valueOf(UUID.randomUUID());
        this.userId = userId;
        this.userName = userName;
        this.createDate = LocalDateTime.now();
    }

    public Session(Integer id, String sessionId, String userId, String userName, LocalDateTime createDate) {
        this.id = id;
        this.sessionId = sessionId;
        this.userId = userId;
        this.userName = userName;
        this.createDate = createDate;
    }

    public Session(Integer id, String userId, String userName, String userUuid) {
        this(id, userId, userName);
        this.userUuid = userUuid;
    }

    public Integer getId() {
        return id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getCreateDate() {
            return createDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
