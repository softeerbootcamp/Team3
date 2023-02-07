package lightning.gathergo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.util.UUID;

public class User {
    @Id
    private Integer id;

    private String uuid;

    @Column("userid")
    private String userId;

    @Column("username")
    private String userName;

    private String password;

    private String email;

    private String introduction;  // 한 줄 소개

    @Column("profilepath")
    private String profilePath;  // 프로필 이미지 경로

    public User(String uuid, String userId, String userName, String password, String email, String introduction, String profilePath) {
        this.uuid = uuid;
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.introduction = introduction;
        this.profilePath = profilePath;
    }

    public User(String userId, String userName, String password, String email) {
        this(String.valueOf(UUID.randomUUID()), userId, userName, password, email, "", "");
    }

    public User() { }

    public String getUuid() {
        return uuid;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
