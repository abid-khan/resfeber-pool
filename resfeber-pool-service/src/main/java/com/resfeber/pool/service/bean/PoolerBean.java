package com.resfeber.pool.service.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import com.resfeber.pool.core.type.PaymentStatus;

@Getter
@Setter
@Builder(toBuilder = true)
public class PoolerBean {
    private PoolBean pool;
    private UserBean user;
    private PaymentStatus paymentStatus;
}
