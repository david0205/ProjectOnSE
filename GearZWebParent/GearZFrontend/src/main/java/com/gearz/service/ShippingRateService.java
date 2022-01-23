package com.gearz.service;

import com.gearz.common.entity.Address;
import com.gearz.common.entity.Customer;
import com.gearz.common.entity.ShippingRate;
import com.gearz.repository.ShippingRateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingRateService {

    @Autowired
    private ShippingRateRepository repo;

    public ShippingRate getShippingRateForCustomer(Customer customer) {
        String district = customer.getDistrict().getName();
        if (district == null || district.isEmpty()) {
            district = customer.getCity().getName();
        }
        return repo.findByCityAndDistrict(customer.getCity(), district);
    }

    public ShippingRate getShippingRateForAddress(Address address) {
        String district = address.getDistrict().getName();
        if (district == null || district.isEmpty()) {
            district = address.getCity().getName();
        }
        return repo.findByCityAndDistrict(address.getCity(), district);
    }
}
