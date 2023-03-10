package lightning.gathergo.dto;

import lightning.gathergo.model.Article;
import lightning.gathergo.model.User;

import java.util.List;

public class UserDto {
    public static class GetProfileResponse {
        private String uuid;

        private String userId;

        private String userName;

        private String email;

        private String introduction;
        private String profilePath;
        private List<GatheringDto.ArticlePartialDto> articleList; //해당 유저가 속한 전체 모임들
        private List<GatheringDto.ArticlePartialDto> hostingArticleList; // 해당 유저가 호스팅한 모임들

        public GetProfileResponse(String uuid, String userId, String userName, String email, String introduction, String profilePath, List<GatheringDto.ArticlePartialDto> articleList, List<GatheringDto.ArticlePartialDto> hostingArticleList) {
            this.uuid = uuid;
            this.userId = userId;
            this.userName = userName;
            this.email = email;
            this.introduction = introduction;
            this.profilePath = profilePath;

            // 사본 생성
            this.articleList = List.copyOf(articleList);
            this.hostingArticleList = List.copyOf(hostingArticleList);
        }

        public GetProfileResponse(User user, List<GatheringDto.ArticlePartialDto> articleList, List<GatheringDto.ArticlePartialDto> hostingArticleList) {
            this(user.getUuid(), user.getUserId(), user.getUserName(), user.getEmail(), user.getIntroduction(), user.getProfilePath(), articleList, hostingArticleList);
        }

        public GetProfileResponse() {
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

        public List<GatheringDto.ArticlePartialDto> getArticleList() {
            return articleList;
        }

        public void setArticleList(List<GatheringDto.ArticlePartialDto> articleList) {
            this.articleList = List.copyOf(articleList);
        }

        public List<GatheringDto.ArticlePartialDto> getHostingArticleList() {
            return hostingArticleList;
        }

        public void setHostingArticleList(List<GatheringDto.ArticlePartialDto> hostingArticleList) {
            this.hostingArticleList = List.copyOf(hostingArticleList);
        }
    }


    public static class PutProfile {
        private String introduction;

        public PutProfile(String introduction) {
            this.introduction = introduction;
        }

        public PutProfile() {
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }
    }
}
