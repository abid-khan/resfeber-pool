package com.resfeber.pool.service;

import java.util.List;

import com.resfeber.pool.core.type.Status;
import com.resfeber.pool.service.bean.PoolBean;

public interface PoolService {
    PoolBean findByUuid(String uuid);

    List<PoolBean> findByUserUuid(String uuid);

    List<PoolBean> findByStatus(Status status);

    PoolBean schedulePool(PoolBean pool);

    PoolBean updateStatus(PoolBean poolBean, Status status);

    void delete(PoolBean pool);
}
