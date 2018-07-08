package com.resfeber.pool.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.resfeber.pool.core.type.Status;
import com.resfeber.pool.data.model.Pool;
import com.resfeber.pool.data.model.Pooler;
import com.resfeber.pool.data.model.User;

@Repository
public interface PoolerRepository extends JpaRepository<Pooler, Long> {
    Pooler findByPoolAndUserAndStatus(Pool pool, User user, Status status);

    @Query("select pooler from  Pooler pooler where pooler.user.uuid=?1")
    List<Pooler> findByUserUuid(String uuid);
}
