package lightning.gathergo.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    NO_RESOURCE(HttpStatus.NOT_FOUND,"해당 리소스가 존재하지 않습니다"),
    NAME_DUPLICATED(HttpStatus.CONFLICT,"이미 해당명의 리소스가 존재합니다"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
