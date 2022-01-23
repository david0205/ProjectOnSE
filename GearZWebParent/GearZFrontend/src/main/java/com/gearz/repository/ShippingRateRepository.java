package com.gearz.repository;

import com.gearz.common.entity.City;
import com.gearz.common.entity.ShippingRate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRateRepository extends JpaRepository<ShippingRate, Integer> {

    public ShippingRate findByCityAndDistrict(City city, String district);
}
