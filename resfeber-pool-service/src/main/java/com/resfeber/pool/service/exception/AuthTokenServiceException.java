package com.resfeber.pool.service.exception;

public class AuthTokenServiceException extends RuntimeException {
    private String code;

    public AuthTokenServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public AuthTokenServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
