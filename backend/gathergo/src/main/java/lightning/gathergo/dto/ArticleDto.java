package lightning.gathergo.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.sql.Date;

public class ArticleDto {

    // TODO regionId, categoryId 부분 develop 브랜치에 머지한 후 Region, Category 객체로 바꾸기

    public static class Response{
        private String uuid;
        private Long hostId;
        private String title;
        private String thumbnail;
        private int curr; // 추후에 int를 Integer로 바꾸기!!!!!
        private int total;
        private Boolean isClosed;
        private String content;
        private Date meetingDay;
        private String location;
        private int regionId;
        private int categoryId;
        //private List<Comment> comments;

        public Long getHostId() {
            return hostId;
        }

        public void setHostId(Long hostId) {
            this.hostId = hostId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
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

        public Date getMeetingDay() {
            return meetingDay;
        }

        public void setMeetingDay(Date meetingDay) {
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
    }

    public static class CreateRequest{
        private Long hostId;
        private String title;
        private int categoryId;
        private String location;
        private int regionId;
        private int total;
        private String content;
        private Date meetingDay;
        //private List<Comment> comments;

        public Long getHostId() {
            return hostId;
        }

        public void setHostId(Long hostId) {
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

        public Date getMeetingDay() {
            return meetingDay;
        }

        public void setMeetingDay(Date meetingDay) {
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
        private String uuid;
        private String title;
        private int categoryId;
        private String location;
        private int regionId;
        private int total;
        private Date meetingDay;
        private String content;
        //private List<Comment> comments;

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

        public Date getMeetingDay() {
            return meetingDay;
        }

        public void setMeetingDay(Date meetingDay) {
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
        String uuid;
        Boolean isClosed = true;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public Boolean getClosed() {
            return isClosed;
        }

        public void setClosed(Boolean closed) {
            isClosed = closed;
        }
    }
}
