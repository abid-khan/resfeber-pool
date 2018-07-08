package com.resfeber.pool.api.ws;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import com.resfeber.pool.core.type.VehicleType;

@Data
@Getter
@Setter
public class VehicleWS extends BaseWS {
    @NotEmpty(message = "number.not.empty")
    private String number;

    @NotEmpty(message = "brand.not.empty")
    private String brand;

    private String description;

    @NotEmpty(message = "type.not.empty")
    private VehicleType type;

    private UserWS user;

    @NotEmpty(message = "capacity.not.empty")
    private Integer capacity;

    @Builder
    private VehicleWS(String uuid, String number, String brand, String description, VehicleType type, UserWS user, Integer capacity) {
        super(uuid);
        this.number = number;
        this.brand = brand;
        this.description = description;
        this.type = type;
        this.user = user;
        this.capacity = capacity;
    }
}
