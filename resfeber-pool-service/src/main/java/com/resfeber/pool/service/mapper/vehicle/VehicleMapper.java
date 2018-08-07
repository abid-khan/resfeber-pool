package com.resfeber.pool.service.mapper.vehicle;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.resfeber.pool.core.mapper.Mapper;
import com.resfeber.pool.data.model.Vehicle;
import com.resfeber.pool.data.repository.UserRepository;
import com.resfeber.pool.service.bean.VehicleBean;
import com.resfeber.pool.service.mapper.user.UserMapper;

@Component
public class VehicleMapper implements Mapper<Vehicle, VehicleBean> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public VehicleBean fromSource(Vehicle vehicle) {
        return Objects.isNull(vehicle) ? null : VehicleBean.builder().id(vehicle.getId()).uuid(vehicle.getUuid()).status(vehicle.getStatus().toString()).number(vehicle.getNumber()).brand(vehicle.getBrand()).capacity(vehicle.getCapacity()).description(vehicle.getDescription()).type(vehicle.getType()).build();
    }

    @Override
    public Vehicle toSource(VehicleBean vehicleBean) {
        return Objects.isNull(vehicleBean) ? null : Vehicle.builder().number(vehicleBean.getNumber()).brand(vehicleBean.getBrand()).description(vehicleBean.getDescription()).type(vehicleBean.getType()).capacity(vehicleBean.getCapacity()).user(userRepository.findByUuid(vehicleBean.getUser().getUuid())).build();
    }
}
