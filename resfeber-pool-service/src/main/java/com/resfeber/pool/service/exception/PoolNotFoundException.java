package com.resfeber.pool.service.exception;


public class PoolNotFoundException extends RuntimeException {
    private String code;

    public PoolNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }

    public PoolNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
