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
    }

    protected static class SignupResponse {
        protected String message;
        protected String redirectUrl;

        protected SignupResponse(String message, String redirectUrl) {
            this.message = message;
            this.redirectUrl = redirectUrl;
        }
    }

    public static class SignupSuccessfulResponse extends SignupDto.SignupResponse {
        private Session session;

        public SignupSuccessfulResponse(String message, String redirectUrl) {
            super(message, redirectUrl);
        }

        public Session getSession() {
            return session;
        }
    }

    public static class SignupFailedResponse extends SignupDto.SignupResponse {
        public SignupFailedResponse(String message, String redirectUrl) {
            super(message, redirectUrl);
        }

        public String getMessage() {
            return message;
        }
    }
}
