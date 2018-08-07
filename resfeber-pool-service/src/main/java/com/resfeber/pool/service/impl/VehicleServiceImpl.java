package com.resfeber.pool.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resfeber.pool.core.type.Status;
import com.resfeber.pool.data.model.User;
import com.resfeber.pool.data.model.Vehicle;
import com.resfeber.pool.data.repository.UserRepository;
import com.resfeber.pool.data.repository.VehicleRepository;
import com.resfeber.pool.service.VehicleService;
import com.resfeber.pool.service.bean.VehicleBean;
import com.resfeber.pool.service.exception.UserNotFoundException;
import com.resfeber.pool.service.exception.VehicleServiceException;
import com.resfeber.pool.service.mapper.vehicle.VehicleMapper;

@Slf4j
@Service
@Transactional(readOnly = true)
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleMapper vehicleMapper;

    @Override
    public VehicleBean findByUuid(String uuid) {
        try {
            Vehicle vehicle = vehicleRepository.findByUuid(uuid);
            return vehicleMapper.fromSource(vehicle);
        } catch (Exception ex) {
            log.error("Failed to find  vehicle for uuid {} due to {}", uuid, ex);
            throw new VehicleServiceException("Failed to find  vehicle", ex);
        }
    }

    @Override
    public VehicleBean findByUser(String userUuid) {
        try {
            User user = userRepository.findByUuid(userUuid);
            Vehicle vehicle = vehicleRepository.findByUserAndStatus(user, Status.ACTIVE);
            return vehicleMapper.fromSource(vehicle);
        } catch (Exception ex) {
            log.error("Failed to fetch vehicle for user {} due to {}", userUuid, ex);
            throw new VehicleServiceException("Failed to fetch vehicle", ex);
        }
    }

    @Override
    public List<VehicleBean> findByStatus(Status status) {
        try {

            List<Vehicle> vehicles = vehicleRepository.findByStatus(Status.ACTIVE);
            return vehicles.stream().filter(v -> Objects.nonNull(v)).map(v -> vehicleMapper.fromSource(v)).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Failed to fetch vehicles for status {} due to {}", status, ex);
            throw new VehicleServiceException("Failed to fetch vehicles", ex);
        }
    }

    @Transactional
    @Override
    public VehicleBean saveOrUpdate(VehicleBean vehicleBean) {
        try {
            Vehicle vehicle = vehicleRepository.findByUuid(vehicleBean.getUuid());
            if (Objects.isNull(vehicle)) {
                vehicle = vehicleMapper.toSource(vehicleBean);
            } else {
                //TODO
            }

            if (Objects.isNull(vehicle.getUser())) {
                throw new UserNotFoundException("NOT_FOUND", "add.phone.number");
            }
            vehicle = vehicleRepository.saveAndFlush(vehicle);
            return vehicleMapper.fromSource(vehicle);
        } catch (Exception ex) {
            log.error("Failed to save vehicle {} due to {}", vehicleBean, ex);
            throw new VehicleServiceException("Failed to save vehicle", ex);
        }
    }

    @Transactional
    @Override
    public void delete(VehicleBean vehicleBean) {
        try {
            Vehicle vehicle = vehicleRepository.findByUuid(vehicleBean.getUuid());
            vehicle.setStatus(Status.DELETED);
            vehicleRepository.saveAndFlush(vehicle);
        } catch (Exception ex) {
            log.error("Failed to delete Vehicle {} due to {}", vehicleBean, ex);
            throw new VehicleServiceException("Failed to delete vehicle", ex);
        }
    }
}
