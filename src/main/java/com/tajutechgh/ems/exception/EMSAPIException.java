package com.tajutechgh.ems.exception;

import org.springframework.http.HttpStatus;

public class EMSAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public EMSAPIException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
