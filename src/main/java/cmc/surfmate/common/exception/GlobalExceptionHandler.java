package cmc.surfmate.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.BindException;

/**
 * GlobalExceptionHandler.java
 *
 * @author jemlog
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            BindException.class,
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<GlobalExceptionResponse> badRequest(Exception e) {

        return ResponseEntity.badRequest()
                .body(new GlobalExceptionResponse(ExceptionCodeAndDetails.BAD_REQUEST.getCode(), ExceptionCodeAndDetails.BAD_REQUEST.getMessage()));
    }
}
