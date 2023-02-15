package lightning.gathergo.dto;

import lightning.gathergo.model.Article;

import java.util.List;

public class UserDto {
    public static class Response{
        private String uuid;

        private String userId;

        private String userName;

        private String password;

        private String email;

        private String introduction;
        private String profilePath;
        private List<Article> articleList; //해당 유저가 속한 전체 모임들
        private List<Article> hostingArticleList; // 해당 유저가 호스팅한 모임들

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getProfilePath() {
            return profilePath;
        }

        public void setProfilePath(String profilePath) {
            this.profilePath = profilePath;
        }

        public List<Article> getArticleList() {
            return articleList;
        }

        public void setArticleList(List<Article> articleList) {
            this.articleList = articleList;
        }

        public List<Article> getHostingArticleList() {
            return hostingArticleList;
        }

        public void setHostingArticleList(List<Article> hostingArticleList) {
            this.hostingArticleList = hostingArticleList;
        }
    }

}
