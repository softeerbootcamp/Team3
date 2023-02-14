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
    public static class Response{
        private String uuid;
        private String hostId;
        private String hostIntroduction;
        private String title;
        private int total;
        private Boolean isClosed;
        private String content;
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp meetingDay;
        private String location;
        private int regionId;
        private int categoryId;

        public String getHostId() {
            return hostId;
        }

        public void setHostId(String hostId) {
            this.hostId = hostId;
        }

        public String getHostIntroduction() {
            return hostIntroduction;
        }

        public void setHostIntroduction(String hostIntroduction) {
            this.hostIntroduction = hostIntroduction;
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
    }

    public static class CreateRequest{
        private String title;
        private int categoryId;
        private String location;
        private int total;
        private String content;
        @JsonSerialize(as = Timestamp.class)
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp meetingDay;

        public CreateRequest() {
        }

        public CreateRequest(String title, int categoryId, String location, int total, String content, Timestamp meetingDay) {
            this.title = title;
            this.categoryId = categoryId;
            this.location = location;
            this.total = total;
            this.content = content;
            this.meetingDay = meetingDay;
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

    public static class JoinRequest{
        private boolean hasJoined;

        public boolean isHasJoined() {
            return hasJoined;
        }

        public void setHasJoined(boolean hasJoined) {
            this.hasJoined = hasJoined;
        }
    }
}
