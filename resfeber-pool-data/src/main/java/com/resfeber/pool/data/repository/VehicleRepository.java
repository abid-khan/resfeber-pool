package com.resfeber.pool.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resfeber.pool.core.type.Status;
import com.resfeber.pool.data.model.User;
import com.resfeber.pool.data.model.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Vehicle findByUser(User user);

    List<Vehicle> findByStatus(Status status);

    Vehicle findByUuid(String uuid);

    Vehicle findByUserAndStatus(User user, Status status);
}
