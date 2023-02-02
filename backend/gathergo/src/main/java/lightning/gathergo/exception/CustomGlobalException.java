package lightning.gathergo.exception;

import org.springframework.http.HttpStatus;

public class CustomGlobalException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;
    private final HttpStatus status;

    public CustomGlobalException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
