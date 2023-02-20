package lightning.gathergo.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    NO_RESOURCE(HttpStatus.NOT_FOUND,"해당 리소스가 존재하지 않습니다"),
    NAME_DUPLICATED(HttpStatus.CONFLICT,"이미 해당명의 리소스가 존재합니다"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),
    DUPLICATE_KEY(HttpStatus.CONFLICT, "이미 존재하는 식별자입니다."),
    ALREADY_FULLED(HttpStatus.TEMPORARY_REDIRECT, "이미 가득 찬 모임입니다."),
    INVALID_DATE(HttpStatus.BAD_REQUEST, "모임 시간을 현재시간 이전으로 설정할 수 없습니다."),
    INVALID_TOTAL(HttpStatus.BAD_REQUEST, "총원은 2명 이상이어야 합니다.");

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
