package com.resfeber.pool.service.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import com.resfeber.pool.core.type.PaymentStatus;

@Getter
@Setter
public class PoolerBean extends BaseBean {
    private PoolBean pool;
    private UserBean user;
    private PaymentStatus paymentStatus;

    @Builder
    private PoolerBean(Long id, String uuid, String status, PoolBean pool, UserBean user, PaymentStatus paymentStatus) {
        super(id, uuid, status);
        this.pool = pool;
        this.user = user;
        this.paymentStatus = paymentStatus;
    }
}
