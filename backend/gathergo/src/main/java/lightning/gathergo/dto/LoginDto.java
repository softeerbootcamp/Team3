package lightning.gathergo.dto;

import lightning.gathergo.model.Session;

public class LoginDto {

    public static class LoginInput {
        private String userId;
        private String password;

        public LoginInput(String userId, String password) {
            this.userId = userId;
            this.password = password;
        }

        public LoginInput() {
        }

        public String getUserId() {
            return userId;
        }

        public String getPassword() {
            return password;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class LoginSuccessfulResponse {
        private String message;
        private Session session;

        public LoginSuccessfulResponse(String message, Session s) {
            this.message = message;
            this.session = s;
        }

        public Session getSession() {
            return session;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class LoginFailedResponse {
        private String message;

        public LoginFailedResponse(String message, String redirectUrl) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
