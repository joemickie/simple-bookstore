package com.chukwuemeka.simple_store.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {

    private String message;

    private Integer statusCode;

    private T data;

    private HttpStatus status;

    public ApiResponse(String message,T data) {
        this.message = message;
        this.statusCode = HttpStatus.OK.value();
        this.data = data;
        this.status = HttpStatus.OK;
    }

    public ApiResponse(String message, T data, HttpStatus status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public ApiResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
    public ApiResponse(T data) {
        this.data = data;
    }
}
