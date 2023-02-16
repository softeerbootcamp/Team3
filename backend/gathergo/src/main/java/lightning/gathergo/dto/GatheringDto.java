package lightning.gathergo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lightning.gathergo.model.Article;

import java.sql.Timestamp;
import java.util.List;

public class GatheringDto {

    // TODO regionId, categoryId 부분 develop 브랜치에 머지한 후 Region, Category 객체로 바꾸기
    // TODO hostId 부분도 user의 uuid를 활용할 수 있도록
    // TODO TimeStamp <- String 변환 안되는 이유 알아내기

    // 응답 관련 dto
    // 게시물 리스트 조회 시 article info list
    public static class ArticleListResponse{
        private List<ArticlePartialDto> articles;

        public ArticleListResponse() {
        }

        public List<ArticlePartialDto> getArticles() {
            return articles;
        }

        public void setArticles(List<ArticlePartialDto> articles) {
            this.articles = articles;
        }
    }
    public static class ArticlePartialDto{
        private String uuid;
        private String title;
        private Integer curr;
        private Integer total;
        private Boolean isClosed;
        private String content;
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp meetingDay;
        private Integer regionId;
        private Integer categoryId;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getCurr() {
            return curr;
        }

        public void setCurr(Integer curr) {
            this.curr = curr;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Boolean getClosed() {
            return isClosed;
        }

        public void setClosed(Boolean closed) {
            isClosed = closed;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Timestamp getMeetingDay() {
            return meetingDay;
        }

        public void setMeetingDay(Timestamp meetingDay) {
            this.meetingDay = meetingDay;
        }

        public Integer getRegionId() {
            return regionId;
        }

        public void setRegionId(Integer regionId) {
            this.regionId = regionId;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public ArticlePartialDto(String uuid, String title, Integer curr, Integer total, Boolean isClosed, String content, Timestamp meetingDay, Integer regionId, Integer categoryId) {
            this.uuid = uuid;
            this.title = title;
            this.curr = curr;
            this.total = total;
            this.isClosed = isClosed;
            this.content = content;
            this.meetingDay = meetingDay;
            this.regionId = regionId;
            this.categoryId = categoryId;
        }

        public ArticlePartialDto(Article article, Integer curr) {
            this.uuid = article.getUuid();
            this.title = article.getTitle();
            this.curr = curr;
            this.total = article.getTotal();
            this.isClosed = article.getClosed();
            this.content = article.getContent();
            this.meetingDay = article.getMeetingDay();
            this.regionId = article.getRegionId();
            this.categoryId = article.getCategoryId();
        }

        public ArticlePartialDto() {
        }
    }

    // 게시물 상세 조회 시 article 상세 정보
    public static class ArticleDetailResponse{
        private ArticleFullDto article;
        private List<CommentDto.Response> comments;
        private UserDto host;

        public ArticleFullDto getArticle() {
            return article;
        }

        public void setArticle(ArticleFullDto article) {
            this.article = article;
            this.article.setHasJoined(false);
            this.article.setIsHost(false);
        }

        public List<CommentDto.Response> getComments() {
            return comments;
        }

        public void setComments(List<CommentDto.Response> comments) {
            this.comments = comments;
        }

        public UserDto getHost() {
            return host;
        }

        public void setHost(UserDto host) {
            this.host = host;
        }
    }
    public static class ArticleFullDto{
        private String uuid;
        private String title;
        private Integer curr;
        private Integer total;
        private Boolean isClosed;
        private String content;
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp meetingDay;
        private String location;
        private String locationDetail;
        private Integer regionId;
        private Integer categoryId;
        private Boolean hasJoined;
        private Boolean isHost;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getCurr() {
            return curr;
        }

        public void setCurr(Integer curr) {
            this.curr = curr;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Boolean getClosed() {
            return isClosed;
        }

        public void setClosed(Boolean closed) {
            isClosed = closed;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Timestamp getMeetingDay() {
            return meetingDay;
        }

        public void setMeetingDay(Timestamp meetingDay) {
            this.meetingDay = meetingDay;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLocationDetail() {
            return locationDetail;
        }

        public void setLocationDetail(String locationDetail) {
            this.locationDetail = locationDetail;
        }

        public Integer getRegionId() {
            return regionId;
        }

        public void setRegionId(Integer regionId) {
            this.regionId = regionId;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Boolean getHasJoined() {
            return hasJoined;
        }

        public void setHasJoined(Boolean hasJoined) {
            this.hasJoined = hasJoined;
        }

        public Boolean getIsHost() {
            return isHost;
        }

        public void setIsHost(Boolean host) {
            isHost = host;
        }
    }

    public static class UserDto{
        // 문자열 유저 아이디
        private String hostId;
        // 한줄소개
        private String hostDesc;
        // 프로필 사진 경로
        private String hostProfile;

        public UserDto() {
        }

        public UserDto(String hostId, String hostDesc, String hostProfile) {
            this.hostId = hostId;
            this.hostDesc = hostDesc;
            this.hostProfile = hostProfile;
        }

        public String getHostId() {
            return hostId;
        }

        public void setHostId(String hostId) {
            this.hostId = hostId;
        }

        public String getHostDesc() {
            return hostDesc;
        }

        public void setHostDesc(String hostDesc) {
            this.hostDesc = hostDesc;
        }

        public String getHostProfile() {
            return hostProfile;
        }

        public void setHostProfile(String hostProfile) {
            this.hostProfile = hostProfile;
        }
    }

    // 게시물 정보가 필요하지 않은 api에대한 dto
    public static class MessageResponse{
        private String message;
        private String articleUuid;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getArticleUuid() {
            return articleUuid;
        }

        public void setArticleUuid(String articleUuid) {
            this.articleUuid = articleUuid;
        }
    }


    // 요청 관련 dto
    public static class CreateRequest{
        private String hostId;
        private String title;
        private int categoryId;
        private String location;
        private String locationDetail;
        private int regionId;
        private int total;
        private String content;
        @JsonSerialize(as = Timestamp.class)
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp meetingDay;

        public CreateRequest() {
        }

        public CreateRequest(String hostId, String title, int categoryId, String location, int regionId, int total, String content, Timestamp meetingDay) {
            this.hostId = hostId;
            this.title = title;
            this.categoryId = categoryId;
            this.location = location;
            this.regionId = regionId;
            this.total = total;
            this.content = content;
            this.meetingDay = meetingDay;
        }

        public String getHostId() {
            return hostId;
        }

        public void setHostId(String hostId) {
            this.hostId = hostId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Timestamp getMeetingDay() {
            return meetingDay;
        }

        public void setMeetingDay(Timestamp meetingDay) {
            this.meetingDay = meetingDay;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLocationDetail() {
            return locationDetail;
        }

        public void setLocationDetail(String locationDetail) {
            this.locationDetail = locationDetail;
        }

        public int getRegionId() {
            return regionId;
        }

        public void setRegionId(int regionId) {
            this.regionId = regionId;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }
    }

    public static class UpdateRequest{
        private String hostId;
        private String title;
        private int categoryId;
        private String location;
        private String locationDetail;
        private int regionId;
        private int total;
        @JsonSerialize(as = Timestamp.class)
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp meetingDay;
        private String content;

        public String getHostId() {
            return hostId;
        }

        public void setHostId(String hostId) {
            this.hostId = hostId;
        }

        public String getLocationDetail() {
            return locationDetail;
        }

        public void setLocationDetail(String locationDetail) {
            this.locationDetail = locationDetail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Timestamp getMeetingDay() {
            return meetingDay;
        }

        public void setMeetingDay(Timestamp meetingDay) {
            this.meetingDay = meetingDay;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getRegionId() {
            return regionId;
        }

        public void setRegionId(int regionId) {
            this.regionId = regionId;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }
    }
}
