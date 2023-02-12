package lightning.gathergo.model;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("subscription")
public class Subscription {

    // TODO: id 칼럼을 이용할지, uuid 칼럼을 이용할지
    @Column("articleId")
    private int articleId;  // 게시글의 id

    @Column("token")
    private String deviceToken;  // 기기 식별 토큰

    public Subscription(int articleId, String deviceToken) {
        this.articleId = articleId;
        this.deviceToken = deviceToken;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
