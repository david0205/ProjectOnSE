package com.gearz.admin.repository;

import java.util.List;

import com.gearz.common.entity.City;
import com.gearz.common.entity.District;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, Integer> {

    public List<District> findAllByOrderByNameAsc();

    public List<District> findByCityOrderByNameAsc(City city);

    public District findByName(String name);
}