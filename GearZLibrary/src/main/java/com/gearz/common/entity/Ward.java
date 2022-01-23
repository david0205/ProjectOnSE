package com.gearz.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "wards")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "district")
public class Ward extends BaseEntity {

    @Column(length = 30, nullable = false)
    @Setter
    @Getter
    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id")
    @Getter
    private District district;
}
