package com.resfeber.pool.data.model;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import com.resfeber.pool.core.type.Authority;

@Entity
@Table(name="authorities")
@Setter
@Getter
@Builder
public class Authorities extends  AbstractAuditableEntity<User> {
    @Enumerated(EnumType.STRING)
    Authority  authority;
    @ManyToOne
    private User user;
}
