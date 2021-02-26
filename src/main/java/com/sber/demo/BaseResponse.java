package com.sber.demo;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;

@Hidden
public class BaseResponse {
    private final String status;
    private final HttpStatus code;

    public BaseResponse(String status, HttpStatus code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public HttpStatus getCode() {
        return code;
    }
}
