package com.resfeber.pool.service.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import com.resfeber.pool.core.type.VehicleType;

@Getter
@Setter
public class VehicleBean extends BaseBean {
    private String number;
    private String brand;
    private String description;
    private VehicleType type;
    private UserBean user;
    private Integer capacity;

    @Builder
    private VehicleBean(Long id, String uuid, String number, String brand, String description, VehicleType type, UserBean user, Integer capacity) {
        super(id, uuid);
        this.number = number;
        this.brand = brand;
        this.description = description;
        this.type = type;
        this.user = user;
        this.capacity = capacity;
    }
}
