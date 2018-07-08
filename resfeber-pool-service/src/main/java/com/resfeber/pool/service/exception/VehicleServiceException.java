package com.resfeber.pool.service.exception;


public class VehicleServiceException extends RuntimeException {
    private String code;

    public VehicleServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public VehicleServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
