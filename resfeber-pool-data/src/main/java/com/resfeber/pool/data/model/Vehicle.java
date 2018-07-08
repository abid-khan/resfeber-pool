package com.resfeber.pool.data.model;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.resfeber.pool.core.type.VehicleType;

@Entity
@Table(name = "vehicles")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle extends AbstractAuditableEntity<User> {
    private String number;
    private String brand;
    private String description;
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    @OneToOne
    private User user;
    private Integer capacity;
}
