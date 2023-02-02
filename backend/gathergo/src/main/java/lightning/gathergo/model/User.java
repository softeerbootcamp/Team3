package lightning.gathergo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

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
    private String introduction;
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
}
