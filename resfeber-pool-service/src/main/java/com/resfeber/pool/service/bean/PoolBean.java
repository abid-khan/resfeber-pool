package com.resfeber.pool.service.bean;


import java.util.Date;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PoolBean extends BaseBean {
    private UserBean user;
    private VehicleBean vehicle;
    private String route;
    private Date startTime;
    private Integer poolerCount;
    private Set<UserBean> poolers;

    @Builder
    private PoolBean(Long id, String uuid,String status, UserBean user, VehicleBean vehicle, String route, Date startTime, Integer poolerCount, Set<UserBean> poolers) {
        super(id, uuid, status);
        this.user = user;
        this.vehicle = vehicle;
        this.route = route;
        this.startTime = startTime;
        this.poolerCount = poolerCount;
        this.poolers = poolers;
    }
}
