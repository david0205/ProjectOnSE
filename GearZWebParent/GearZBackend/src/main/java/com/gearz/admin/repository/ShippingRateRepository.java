package com.gearz.admin.repository;

import com.gearz.common.entity.ShippingRate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ShippingRateRepository extends JpaRepository<ShippingRate, Integer> {

    @Query("SELECT sr FROM ShippingRate sr WHERE sr.city.id = ?1 AND sr.district = ?2")
    public ShippingRate findByCityAndDistrict(Integer cityId, String district);

    @Query("UPDATE ShippingRate sr SET sr.codSupported = ?2 WHERE sr.id = ?1")
    @Modifying
    public void updateCODSupport(Integer id, boolean enabled);

    public Long countById(Integer id);

}
