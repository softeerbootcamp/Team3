package lightning.gathergo.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomGlobalException.class)
    public ResponseEntity<ErrorResponse> handleLineException(final CustomGlobalException error) {
        ErrorResponse errorResponse = new ErrorResponse(error.getErrorCode());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleLineException(final NoSuchElementException error) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.NO_RESOURCE);
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> handleConstraintViolationException(final DuplicateKeyException error) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.DUPLICATE_KEY);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }
}
