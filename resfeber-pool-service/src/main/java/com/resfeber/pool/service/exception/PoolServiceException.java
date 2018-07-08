package com.resfeber.pool.service.exception;

public class PoolServiceException extends RuntimeException {
    private String code;

    public PoolServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public PoolServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
