package com.resfeber.pool.api.ws;


import java.util.Date;
import java.util.Set;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Setter
@Getter
public class PoolWS extends BaseWS {
    private UserWS user;
    private VehicleWS vehicle;
    private String route;
    private Date startTime;
    private Integer poolerCount;
    private Set<UserWS> poolers;

    @Builder
    private PoolWS(String uuid, UserWS user, VehicleWS vehicle, String route, Date startTime, Integer poolerCount, Set<UserWS> poolers) {
        super(uuid);
        this.user = user;
        this.vehicle = vehicle;
        this.route = route;
        this.startTime = startTime;
        this.poolerCount = poolerCount;
        this.poolers = poolers;
    }
}
