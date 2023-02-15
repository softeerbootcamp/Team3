package lightning.gathergo.dto;

import lightning.gathergo.model.Article;
import lightning.gathergo.model.User;

import java.util.List;

public class UserDto {
    public static class ProfileResponse {
        private String uuid;

        private String userId;

        private String userName;

        private String password;

        private String email;

        private String introduction;
        private String profilePath;
        private List<Article> articleList; //해당 유저가 속한 전체 모임들
        private List<Article> hostingArticleList; // 해당 유저가 호스팅한 모임들

        public ProfileResponse(String uuid, String userId, String userName, String password, String email, String introduction, String profilePath, List<Article> articleList, List<Article> hostingArticleList) {
            this.uuid = uuid;
            this.userId = userId;
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.introduction = introduction;
            this.profilePath = profilePath;

            // 사본 생성
            this.articleList = List.copyOf(articleList);
            this.hostingArticleList = List.copyOf(hostingArticleList);
        }

        public ProfileResponse(User user, List<Article> articleList, List<Article> hostingArticleList) {
            this(user.getUuid(), user.getUserId(), user.getUserName(), user.getPassword(), user.getEmail(), user.getIntroduction(), user.getProfilePath(), articleList, hostingArticleList);
        }

        public ProfileResponse() {
        }

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
