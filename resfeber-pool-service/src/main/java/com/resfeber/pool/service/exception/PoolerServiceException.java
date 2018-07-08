package com.resfeber.pool.service.exception;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PoolerServiceException extends   RuntimeException {
    private String code;

    public PoolerServiceException(String code,String message) {
        super(message);
        this.code = code;
    }

    public PoolerServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
