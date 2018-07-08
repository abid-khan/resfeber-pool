package com.resfeber.pool.service.exception;


public class UserServiceException extends RuntimeException {
    private String code;

    public UserServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
