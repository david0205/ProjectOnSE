package com.gearz.admin.repository;

import java.util.List;

import com.gearz.common.entity.City;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {

    public List<City> findAllByOrderByNameAsc();

    public City findByName(String name);
}