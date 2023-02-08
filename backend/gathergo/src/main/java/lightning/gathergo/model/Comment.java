package lightning.gathergo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Table("comment")
public class Comment {

    @Id
    private Integer id;

    @Column("articleid")
    private Integer articleId;  // TODO: 로그인에 사용하는 userId(String) 를 쓸지 Integer User.id를 사용할지

    @Column("userid")
    private Integer userId;  // TODO: 로그인에 사용하는 userId(String) 를 쓸지 Integer User.id를 사용할지

    private Timestamp date;

    private String content;

    private String uuid;

}
