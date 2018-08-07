package com.resfeber.pool.service.impl;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resfeber.pool.core.type.Status;
import com.resfeber.pool.data.model.Pool;
import com.resfeber.pool.data.model.Pooler;
import com.resfeber.pool.data.repository.PoolRepository;
import com.resfeber.pool.data.repository.PoolerRepository;
import com.resfeber.pool.data.repository.UserRepository;
import com.resfeber.pool.data.repository.VehicleRepository;
import com.resfeber.pool.service.PoolService;
import com.resfeber.pool.service.bean.PoolBean;
import com.resfeber.pool.service.exception.PoolNotFoundException;
import com.resfeber.pool.service.exception.PoolServiceException;
import com.resfeber.pool.service.exception.PoolerServiceException;
import com.resfeber.pool.service.mapper.pool.PoolMapper;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PoolServiceImpl implements PoolService {
    @Autowired
    private PoolRepository poolRepository;
    @Autowired
    private PoolMapper poolMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private PoolerRepository poolerRepository;

    @Override
    public PoolBean findByUuid(String uuid) {
        try {
            Pool pool = poolRepository.findByUuid(uuid);
            return Objects.isNull(pool) ? null : poolMapper.fromSource(pool);
        } catch (Exception ex) {
            log.error("Failed to fetch pools for uuid {} due to {}", uuid, ex);
            throw new PoolServiceException("Failed to fetch pools for uuid", ex);
        }
    }

    @Override
    public List<PoolBean> findByUserUuid(String uuid) {
        try {
            List<Pool> pools = poolRepository.findByUserUuidAndStatus(uuid, Status.SCHEDULED);
            return pools.stream().filter(p -> Objects.nonNull(p)).map(p -> poolMapper.fromSource(p)).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Failed to fetch pools for user {} due to {}", uuid, ex);
            throw new PoolServiceException("Failed to fetch pools for user", ex);
        }
    }

    @Override
    public List<PoolBean> findByStatus(Status status) {
        try {
            List<Pool> pools = poolRepository.findByStatus(status);
            return pools.stream().filter(p -> Objects.nonNull(p)).map(p -> poolMapper.fromSource(p)).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Failed to fetch pools for status {} due to {}", status, ex);
            throw new PoolServiceException("Failed to fetch pools for status", ex);
        }
    }

    @Transactional
    @Override
    public PoolBean schedulePool(PoolBean pool) {
        List<Pool> pools = poolRepository.findByUserUuidAndStatus(pool.getUser().getUuid(), Status.SCHEDULED);
        if (!pools.isEmpty()) {
            throw new PoolServiceException("CONFLICT", "user.has.active.pool");
        }

        pools = poolRepository.findByVehicleUuidAndStatus(pool.getVehicle().getUuid(), Status.SCHEDULED);
        if (!pools.isEmpty()) {
            throw new PoolServiceException("CONFLICT", "vehicle.has.active.pool");
        }

        try {
            Pool pool1 = poolMapper.toSource(pool);
            pool1.setStatus(Status.SCHEDULED);
            pool1 = poolRepository.saveAndFlush(pool1);
            return poolMapper.fromSource(pool1);
        } catch (Exception ex) {
            log.error("Failed to save pool {} due to {}", pool, ex);
            throw new PoolServiceException("Failed to save pool", ex);
        }
    }

    @Override
    public void delete(PoolBean pool) {
        try {
            Pool pool1 = poolRepository.findByUuid(pool.getUuid());
            pool1.setStatus(Status.DELETED);
            poolRepository.saveAndFlush(pool1);
        } catch (Exception ex) {
            log.error("Failed to delete pool {} due to {}", pool, ex);
            throw new PoolServiceException("Failed to delete pool", ex);
        }
    }

    @Transactional
    @Override
    public PoolBean updateStatus(PoolBean poolBean, Status status) {
        Pool pool1 = poolRepository.findByUuid(poolBean.getUuid());
        if (Objects.isNull(pool1)) {
            throw new PoolNotFoundException("NOT_FOUND", "pool.not.found");
        }

        if (!poolBean.getUser().getUuid().equals(pool1.getUser().getUuid())) {
            throw new PoolServiceException("PRE_CONDITION_FAILED", "pool.user.different");
        }

        if(Status.CANCELLED.equals(pool1.getStatus()) ||  Status.COMPLETED.equals(pool1.getStatus())){
            throw new PoolServiceException("PRE_CONDITION_FAILED", "pool.status.immutable");
        }

        try {
            Pool pool = poolRepository.findByUuid(poolBean.getUuid());
            pool.setStatus(status);
            List<Pooler> poolers = pool.getPoolers().stream().filter(pooler -> Objects.nonNull(pooler)).map(user -> {
                Pooler pooler = poolerRepository.findByPoolAndUserAndStatus(pool, user, Status.ACTIVE);
                pooler.setStatus(status);
                return pooler;
            }).collect(Collectors.toList());
            poolerRepository.saveAll(poolers);
            //TODO send notification to pooles
            return poolMapper.fromSource(pool);
        } catch (Exception ex) {
            log.error("Failed to update pool {} due to {}", poolBean, ex);
            throw new PoolerServiceException("Booking failed for pool", ex);
        }
    }
}
