package com.ddd.demo.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BussinessException extends RuntimeException {
    private final String code;
    private final HttpStatus httpStatus;

    public BussinessException(String code, HttpStatus httpStatus) {
        super(code);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
