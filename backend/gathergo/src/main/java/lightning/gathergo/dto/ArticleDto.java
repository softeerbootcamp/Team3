package lightning.gathergo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lightning.gathergo.model.Comment;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ArticleDto {

    // TODO regionId, categoryId 부분 develop 브랜치에 머지한 후 Region, Category 객체로 바꾸기
    // TODO hostId 부분도 user의 uuid를 활용할 수 있도록
    // TODO TimeStamp <- String 변환 안되는 이유 알아내기

    public static class Response{
        private String uuid;
        private String hostId;
        private String title;
        private int curr; // 추후에 int를 Integer로 바꾸기!!!!!
        private int total;
        private Boolean isClosed;
        private String content;
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp meetingDay;
        private String location;
        private int regionId;
        private int categoryId;
        private List<CommentDto.Response> comments = new ArrayList<>();

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

        public int getCurr() {
            return curr;
        }

        public void setCurr(int curr) {
            this.curr = curr;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
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

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public List<CommentDto.Response> getComments() {
            return comments;
        }

        public void setComments(List<CommentDto.Response> comments) {
            this.comments = comments;
        }
    }

    public static class GetArticlesResponse{
        private List<Response> articles;
        private Integer count;

        public List<Response> getArticles() {
            return articles;
        }

        public void setArticles(List<Response> articles) {
            this.articles = articles;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    public static class CreateRequest{
        private String hostId;
        private String title;
        private int categoryId;
        private String location;
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
        private String title;
        private int categoryId;
        private String location;
        private int regionId;
        private int total;
        @JsonSerialize(as = Timestamp.class)
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp meetingDay;
        private String content;
        //private List<Comment> comments;

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

    public static class CloseRequest{
        Boolean isClosed = true;

        public Boolean getClosed() {
            return isClosed;
        }

        public void setClosed(Boolean closed) {
            isClosed = closed;
        }
    }

    public static class ReadRequest{
        private String uuid;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }

}
