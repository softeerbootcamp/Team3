package lightning.gathergo.dto;

import lightning.gathergo.model.Session;
import lightning.gathergo.model.User;
import org.springframework.data.relational.core.mapping.Column;

import java.util.UUID;

public class SignupDto {
    public static class SignupInput {
        @Column("userid")
        private String userId;

        @Column("username")
        private String userName;
        private String password;
        private String email;

        public SignupInput() {

        }

        public SignupInput(String userId, String userName, String password, String email) {
            this.userId = userId;
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        public String getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    protected static class SignupResponse {
        protected String message;

        protected SignupResponse(String message) {
            this.message = message;
        }
    }

    public static class SignupSuccessfulResponse extends SignupDto.SignupResponse {
        private Session session;

        public SignupSuccessfulResponse(String message) {
            super(message);
        }

        public Session getSession() {
            return session;
        }
    }

    public static class SignupFailedResponse extends SignupDto.SignupResponse {
        public SignupFailedResponse(String message) {
            super(message);
        }

        public String getMessage() {
            return message;
        }
    }
}
