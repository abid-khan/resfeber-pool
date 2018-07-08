package com.resfeber.pool.data.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.resfeber.pool.core.type.Gender;

@Entity
@Table(name = "users")
@Setter
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractAuditableEntity<User> {
    private String externalId;

    @NotEmpty(message = "email.not.empty")
    @Size(max = 255, message = "email.max.size")
    private String email;

    @Size(max = 255, message = "firstName.max.size")
    @NotNull(message = "firstName.not.null")
    private String firstName;

    @Size(max = 255, message = "lastName.max.size")
    @NotNull(message = "lastName.not.null")
    private String lastName;

    private String givenName;

    private String profile;

    private String avatar;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phone;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Authorities> authorities;

    @OneToMany
    @JoinTable(name = "poolers", joinColumns = {@JoinColumn(name = "user")}, inverseJoinColumns = {@JoinColumn(name = "pool")})
    private Set<Pool> pools;
}
