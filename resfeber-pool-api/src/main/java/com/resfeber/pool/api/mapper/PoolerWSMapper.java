package com.resfeber.pool.api.mapper;

import java.text.ParseException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.resfeber.pool.api.ws.PoolerWS;
import com.resfeber.pool.core.mapper.Mapper;
import com.resfeber.pool.service.bean.PoolerBean;

@Component
public class PoolerWSMapper implements Mapper<PoolerBean, PoolerWS> {

    @Autowired
    private PoolWSMapper poolWSMapper;

    @Autowired
    private UserWSMapper userWSMapper;

    @Override
    public PoolerWS fromSource(PoolerBean poolerBean) {
        return Objects.isNull(poolerBean) ? null : PoolerWS.builder().uuid(poolerBean.getUuid()).status(poolerBean.getStatus()).pool((poolWSMapper.fromSource(poolerBean.getPool()))).user(userWSMapper.fromSource(poolerBean.getUser())).paymentStatus(poolerBean.getPaymentStatus()).build();
    }

    @Override
    public PoolerBean toSource(PoolerWS poolerWS) throws ParseException {
        return Objects.isNull(poolerWS) ? null : PoolerBean.builder().uuid(poolerWS.getUuid()).pool(poolWSMapper.toSource(poolerWS.getPool())).user(userWSMapper.toSource(poolerWS.getUser())).build();
    }
}
