package com.resfeber.pool.data.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.resfeber.pool.core.type.PaymentStatus;

@Entity
@Table(name = "poolers")
@Setter
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Pooler extends AbstractAuditableEntity<User> {

    @JoinColumn(name = "pool_id", nullable = false)
    @ManyToOne
    private Pool pool;

    @JoinColumn(name = "user_id",  nullable = false)
    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
