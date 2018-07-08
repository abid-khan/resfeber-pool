package com.resfeber.pool.api.exception;


public class AunAuthorizedException extends RuntimeException {
    private int code;

    public AunAuthorizedException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
