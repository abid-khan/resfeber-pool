package com.resfeber.pool.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.resfeber.pool.core.type.Status;
import com.resfeber.pool.data.model.Pool;
import com.resfeber.pool.data.model.User;

@Repository
public interface PoolRepository extends JpaRepository<Pool, Long> {
    List<Pool> findByStatus(Status status);

    Pool findByUuid(String uuid);

    Pool findByUuidAndStatus(String uuid, Status status);

    List<Pool> findByUser(User user);

    List<Pool> findByUserAndStatus(User user, Status status);

    @Query("select pool from  Pool pool where pool.user.uuid=?1")
    List<Pool> findByUserUuid(String uuid);


    @Query("select pool from  Pool pool where pool.user.uuid=?1 and pool.status=?2")
    List<Pool> findByUserUuidAndStatus(String uuid, Status status);

    @Query("select pool from  Pool pool where pool.vehicle.uuid=?1 and pool.status=?2")
    List<Pool> findByVehicleUuidAndStatus(String uuid, Status status);
}
