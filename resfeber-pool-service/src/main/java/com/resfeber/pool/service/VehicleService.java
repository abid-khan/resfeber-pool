package com.resfeber.pool.service;


import java.util.List;

import com.resfeber.pool.core.type.Status;
import com.resfeber.pool.service.bean.VehicleBean;

public interface VehicleService {
    List<VehicleBean> findByStatus(Status status);

    VehicleBean saveOrUpdate(VehicleBean vehicleBean);

    void delete(VehicleBean pool);
}
