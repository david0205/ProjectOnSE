package com.gearz.admin.repository;

import java.util.List;

import com.gearz.common.entity.District;
import com.gearz.common.entity.Ward;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WardRepository extends JpaRepository<Ward, Integer> {

    public List<Ward> findByDistrictOrderByNameAsc(District district);
}
