package com.gearz.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shipping_rates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ShippingRate extends BaseEntity {

    private float rate;

    private int days;

    @Column()
    private boolean codSupported;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(nullable = false, length = 30)
    private String district;
}
