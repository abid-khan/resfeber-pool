package com.resfeber.pool.data.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.resfeber.pool.core.type.Status;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class AbstractAuditableEntity<U> extends AbstractPersistable<Long> {
    @Version
    @Column(name = "version")
    protected Long version;

    @Enumerated(EnumType.STRING)
    protected Status status;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;


    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastModifiedDate;

    protected String uuid;

    @ManyToOne
    @JoinColumn(name = "created_by")
    protected U createdBy;

    @ManyToOne
    @JoinColumn(name = "last_modified_by")
    protected U lastModifiedBy;

    @PrePersist
    public void prePersist(){
        uuid = UUID.randomUUID().toString();
        status = Status.ACTIVE;
    }
}
