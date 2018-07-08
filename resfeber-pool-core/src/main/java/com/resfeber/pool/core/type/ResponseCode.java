package com.resfeber.pool.core.type;

public enum ResponseCode {
    OK(200, "OK");

    private ResponseCode(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    private int code;
    private String reason;
}
