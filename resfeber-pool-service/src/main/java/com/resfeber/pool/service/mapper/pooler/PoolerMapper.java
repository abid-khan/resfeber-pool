package com.resfeber.pool.service.mapper.pooler;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.resfeber.pool.core.mapper.Mapper;
import com.resfeber.pool.data.model.Pooler;
import com.resfeber.pool.service.bean.PoolerBean;
import com.resfeber.pool.service.mapper.pool.PoolMapper;
import com.resfeber.pool.service.mapper.user.UserMapper;

@Component
public class PoolerMapper implements Mapper<Pooler, PoolerBean> {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PoolMapper poolMapper;

    @Override
    public PoolerBean fromSource(Pooler pooler) {
        return Objects.isNull(pooler)? null : PoolerBean.builder().uuid(pooler.getUuid()).status(pooler.getStatus().toString()).pool(poolMapper.fromSource(pooler.getPool())).user(userMapper.fromSource(pooler.getUser())).paymentStatus(pooler.getPaymentStatus()).build();
    }

    @Override
    public Pooler toSource(PoolerBean poolerBean) {
        return null;
    }
}
