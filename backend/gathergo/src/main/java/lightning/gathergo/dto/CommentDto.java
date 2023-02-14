package lightning.gathergo.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class CommentDto {

    public static class Response{
        private String uuid;
        private String userId;
        private String content;
        private Timestamp date;

        public Response(){}

        public Response(String uuid, String userId, String content, Timestamp date) {
            this.uuid = uuid;
            this.userId = userId;
            this.content = content;
            this.date = date;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Timestamp getDate() {
            return date;
        }

        public void setDate(Timestamp date) {
            this.date = date;
        }
    }

    public static class CreateRequest{
        private String content;
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp date;
        private String userId;

        public CreateRequest() {
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Timestamp getDate() {
            return date;
        }

        public void setDate(Timestamp date) {
            this.date = date;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class UpdateReqeust{
        private String content;
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private Timestamp date;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Timestamp getDate() {
            return date;
        }

        public void setDate(Timestamp date) {
            this.date = date;
        }
    }

    public static class DeleteRequest{
        private String uuid;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
