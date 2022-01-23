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

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseAddressLine {

    @ManyToOne
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "default_address")
    private boolean defaultForShipping;

    @Override
    public String toString() {
        String fullAddress = "";
        if (fullName != null && !fullName.isEmpty()) {
            fullAddress += "- <strong>" + fullName + "</strong>";
        }
        if (addressLine != null && !addressLine.isEmpty()) {
            fullAddress += ", " + addressLine;
        }
        if (ward.getName() != null && !ward.getName().isEmpty()) {
            fullAddress += ", " + ward.getName();
        }
        if (district.getName() != null && !district.getName().isEmpty()) {
            fullAddress += ", " + district.getName();
        }
        if (city.getName() != null && !city.getName().isEmpty()) {
            fullAddress += ", " + city.getName() + ".<br>";
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            fullAddress += "- Telephone: " + phoneNumber;
        }

        return fullAddress;
    }
}
