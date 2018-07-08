package com.resfeber.pool.service.exception;


public class UserNotFoundException extends RuntimeException {
    private String code;

    public UserNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
