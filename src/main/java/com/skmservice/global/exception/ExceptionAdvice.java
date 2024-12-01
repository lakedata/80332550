package com.skmservice.global.exception;

import com.skmservice.global.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<CommonResponse<Void>> handleCustomException(CustomException e) {
        log.warn("[*] CustomException: ", e);
        HttpStatus errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode).body(
                CommonResponse.onFailure(
                        String.valueOf(errorCode.value()),
                        errorCode.getReasonPhrase()
                )
        );
    }
}
