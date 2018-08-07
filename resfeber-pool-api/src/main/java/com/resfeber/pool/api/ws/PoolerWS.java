package com.resfeber.pool.api.ws;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import com.resfeber.pool.core.type.PaymentStatus;

@Data
@Getter
@Setter
public class PoolerWS extends BaseWS {
    private PoolWS pool;
    private UserWS user;
    private PaymentStatus paymentStatus;

    @Builder
    private PoolerWS(String uuid, String status, PoolWS pool, UserWS user, PaymentStatus paymentStatus) {
        super(uuid, status);
        this.pool = pool;
        this.user = user;
        this.paymentStatus = paymentStatus;
    }
}
