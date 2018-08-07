package com.resfeber.pool.api.mapper;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.resfeber.pool.api.ws.VehicleWS;
import com.resfeber.pool.core.mapper.Mapper;
import com.resfeber.pool.service.bean.VehicleBean;

@Component
public class VehicleWSMapper implements Mapper<VehicleBean, VehicleWS> {
    @Autowired
    private UserWSMapper userWSMapper;

    @Override
    public VehicleWS fromSource(VehicleBean vehicleBean) {
        return Objects.isNull(vehicleBean) ? null : VehicleWS.builder().uuid(vehicleBean.getUuid()).status(vehicleBean.getStatus()).number(vehicleBean.getNumber()).description(vehicleBean.getDescription()).brand(vehicleBean.getBrand()).type(vehicleBean.getType()).capacity(vehicleBean.getCapacity()).build();
    }

    @Override
    public VehicleBean toSource(VehicleWS vehicleWS) {
        return Objects.isNull(vehicleWS) ? null : VehicleBean.builder().uuid(vehicleWS.getUuid()).number(vehicleWS.getNumber()).capacity(vehicleWS.getCapacity()).description(vehicleWS.getDescription()).brand(vehicleWS.getBrand()).type(vehicleWS.getType()).user(userWSMapper.toSource(vehicleWS.getUser())).build();
    }
}
