package com.skmservice.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException  extends RuntimeException {

    private final HttpStatus errorCode;

    public CustomException(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }
}
