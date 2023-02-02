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

        public String getUserId() {
            return userId;
        }

        public String getPassword() {
            return password;
        }
    }
    protected static class LoginResponse {
        protected String message;
        protected String redirectUrl;

        protected LoginResponse(String message, String redirectUrl) {
            this.message = message;
            this.redirectUrl = redirectUrl;
        }
    }

    public static class LoginSuccessfulResponse extends LoginResponse {
        private Session session;

        public LoginSuccessfulResponse(String message, Session s, String redirectUrl) {
            super(message, redirectUrl);
            this.session = s;
        }

        public Session getSession() {
            return session;
        }
    }

    public static class LoginFailedResponse extends LoginResponse {
        public LoginFailedResponse(String message, String redirectUrl) {
            super(message, redirectUrl);
        }

        public String getMessage() {
            return message;
        }
    }
}
