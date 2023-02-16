package lightning.gathergo.dto;

public class SubscriptionDto {
    public static class Request {
        private String deviceToken;
        private String articleId;

        public Request(String deviceToken, String articleId) {
            this.deviceToken = deviceToken;
            this.articleId = articleId;
        }

        public Request() {
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }
    }
}
