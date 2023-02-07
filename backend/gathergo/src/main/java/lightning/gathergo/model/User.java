package lightning.gathergo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.util.HashSet;
import java.util.Set;

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

    // private Set<UserArticleRelationship> userArticleRelationships = new HashSet<>();

    public User(String uuid, String userId, String userName, String password, String email, String introduction, String profilePath) {
        this.uuid = uuid;
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.introduction = introduction;
        this.profilePath = profilePath;
    }

    public Integer getId() {
        return id;
    }

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", introduction='" + introduction + '\'' +
                ", profilePath='" + profilePath + '\'' +
                '}';
    }
}
