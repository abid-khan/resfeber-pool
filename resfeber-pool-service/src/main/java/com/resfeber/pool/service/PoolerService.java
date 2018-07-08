package com.resfeber.pool.service;


import java.util.List;

import com.resfeber.pool.service.bean.PoolerBean;

public interface PoolerService {

    PoolerBean book(PoolerBean poolerBean);

    List<PoolerBean> findByUserUuid(String uuid);
}
