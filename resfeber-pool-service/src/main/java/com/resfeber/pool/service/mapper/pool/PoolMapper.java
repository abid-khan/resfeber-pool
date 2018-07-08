package com.resfeber.pool.service.mapper.pool;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.resfeber.pool.core.mapper.Mapper;
import com.resfeber.pool.data.model.Pool;
import com.resfeber.pool.data.repository.UserRepository;
import com.resfeber.pool.data.repository.VehicleRepository;
import com.resfeber.pool.service.bean.PoolBean;
import com.resfeber.pool.service.mapper.user.UserMapper;
import com.resfeber.pool.service.mapper.vehicle.VehicleMapper;

@Component
public class PoolMapper implements Mapper<Pool, PoolBean> {
    @Autowired
    private VehicleMapper vehicleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public PoolBean fromSource(Pool pool) {
        return Objects.isNull(pool) ? null : PoolBean.builder().uuid(pool.getUuid()).vehicle(vehicleMapper.fromSource(pool.getVehicle())).user(userMapper.fromSource(pool.getUser())).startTime(pool.getStartTime()).route(pool.getRoute()).build();
    }

    @Override
    public Pool toSource(PoolBean poolBean) {
        return Objects.isNull(poolBean) ? null : Pool.builder().vehicle(vehicleRepository.findByUuid(poolBean.getVehicle().getUuid())).user(userRepository.findByUuid(poolBean.getUser().getUuid())).startTime(poolBean.getStartTime()).route(poolBean.getRoute()).build();
    }
}
