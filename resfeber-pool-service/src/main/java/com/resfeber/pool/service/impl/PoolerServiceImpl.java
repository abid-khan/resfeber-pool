package com.resfeber.pool.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resfeber.pool.core.type.PaymentStatus;
import com.resfeber.pool.core.type.Status;
import com.resfeber.pool.data.model.Pool;
import com.resfeber.pool.data.model.Pooler;
import com.resfeber.pool.data.model.User;
import com.resfeber.pool.data.repository.PoolRepository;
import com.resfeber.pool.data.repository.PoolerRepository;
import com.resfeber.pool.data.repository.UserRepository;
import com.resfeber.pool.service.PoolerService;
import com.resfeber.pool.service.bean.PoolerBean;
import com.resfeber.pool.service.exception.PoolerServiceException;
import com.resfeber.pool.service.exception.UserNotFoundException;
import com.resfeber.pool.service.mapper.pooler.PoolerMapper;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PoolerServiceImpl implements PoolerService {
    @Autowired
    private PoolerMapper poolerMapper;
    @Autowired
    private PoolRepository poolRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PoolerRepository poolerRepository;

    @Transactional
    @Override
    public PoolerBean book(PoolerBean poolerBean) {
        Pool pool = poolRepository.findByUuidAndStatus(poolerBean.getPool().getUuid(), Status.ACTIVE);
        User user = userRepository.findByUuid(poolerBean.getUser().getUuid());

        if (Objects.isNull(user)) {
            throw new UserNotFoundException("NOT_FOUND", "add.phone.number");
        }
        if (user.getPools().size() > 0) {
            throw new PoolerServiceException("CONFLICT", "already.booked.pool");
        }

        Pooler pooler = poolerRepository.findByPoolAndUserAndStatus(pool, user, Status.ACTIVE);

        if (Objects.nonNull(pooler)) {
            throw new PoolerServiceException("CONFLICT", "already.booked.current.pool");
        }

        if (pool.getPoolers().size() == pool.getVehicle().getCapacity()) {
            throw new PoolerServiceException("CONFLICT", "pool.capacity.exhausted");
        }
        try {
            pooler = Pooler.builder().pool(pool).user(user).paymentStatus(PaymentStatus.UNPAID).build();
            pooler = poolerRepository.saveAndFlush(pooler);
            pool.setPoolerCount(pool.getPoolerCount() + 1);
            poolRepository.saveAndFlush(pool);
            //TODO send notification
            return poolerMapper.fromSource(pooler);
        } catch (Exception ex) {
            log.error("Booking failed for pool {} due to {}", poolerBean, ex);
            throw new PoolerServiceException("Booking failed for pool", ex);
        }
    }

    @Override
    public List<PoolerBean> findByUserUuid(String uuid) {
        try {
            List<Pooler> poolers = poolerRepository.findByUserUuid(uuid);
            return poolers.stream().filter(p -> Objects.nonNull(p)).map(p -> poolerMapper.fromSource(p)).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Failed to fetch  trips for user {} due to {}", uuid, ex);
            throw new PoolerServiceException("Failed to fetch  trips for user", ex);
        }
    }
}
