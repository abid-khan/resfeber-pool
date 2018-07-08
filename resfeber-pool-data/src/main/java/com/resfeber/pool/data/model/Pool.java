package com.resfeber.pool.data.model;


import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pools")
@Setter
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Pool extends AbstractAuditableEntity<User> {
    @ManyToOne
    private User user;

    @NotNull(message = "vehicle.not.null")
    @ManyToOne
    private Vehicle vehicle;

    @NotEmpty(message = "route.not.empty")
    private String route;

    @NotNull(message = "startTime.not.null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    private Integer poolerCount;

    @OneToMany
    @JoinTable(name = "poolers", joinColumns = {@JoinColumn(name = "pool")}, inverseJoinColumns = {@JoinColumn(name = "user")})
    private Set<User> poolers;
}
