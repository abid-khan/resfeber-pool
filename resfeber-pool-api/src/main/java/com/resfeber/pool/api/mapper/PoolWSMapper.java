package com.resfeber.pool.api.mapper;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.resfeber.pool.api.ws.PoolWS;
import com.resfeber.pool.core.mapper.Mapper;
import com.resfeber.pool.service.bean.PoolBean;

@Component
public class PoolWSMapper implements Mapper<PoolBean, PoolWS> {
    @Autowired
    private UserWSMapper userWSMapper;
    @Autowired
    private VehicleWSMapper vehicleWSMapper;

    @Override
    public PoolWS fromSource(PoolBean poolBean) {
        return Objects.isNull(poolBean) ? null : PoolWS.builder().uuid(poolBean.getUuid()).status(poolBean.getStatus()).user(userWSMapper.fromSource(poolBean.getUser())).vehicle(null).route(poolBean.getRoute()).startTime(poolBean.getStartTime()).poolerCount(poolBean.getPoolerCount()).build();
    }

    @Override
    public PoolBean toSource(PoolWS poolWS) {
        return Objects.isNull(poolWS) ? null : PoolBean.builder().uuid(poolWS.getStatus()).vehicle(vehicleWSMapper.toSource(poolWS.getVehicle())).user(userWSMapper.toSource(poolWS.getUser())).startTime(poolWS.getStartTime()).route(poolWS.getRoute()).build();
    }
}
