package com.fintech.authservice.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;

    public ApplicationException(HttpStatus status, String message) {
        this(status, message, null);
    }

    public ApplicationException(HttpStatus status, String message, String errorCode) {
        super(message);
        this.status = status == null ? HttpStatus.INTERNAL_SERVER_ERROR : status;
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
