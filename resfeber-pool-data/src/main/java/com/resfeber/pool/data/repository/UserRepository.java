package com.resfeber.pool.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resfeber.pool.core.type.Status;
import com.resfeber.pool.data.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndStatus(String email, Status status);

    User findByUuid(String uuid);

    List<User> findByStatus(Status status);
}
