package com.gearz.common.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class BaseAddressLine extends BaseEntity {

    @Column(nullable = false, length = 45)
    @Getter
    @Setter
    protected String fullName;

    @Column(nullable = true, length = 15)
    @Getter
    @Setter
    protected String phoneNumber;

    @Column(nullable = true, length = 64)
    @Getter
    @Setter
    protected String addressLine;
}
