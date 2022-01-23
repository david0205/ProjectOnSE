package com.gearz.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.gearz.common.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

	public Long countById(Integer id);

	public Brand findByName(String name);

	@Query("SELECT NEW Brand(b.id, b.name) FROM Brand b ORDER BY b.name ASC")
	public List<Brand> findAllBrandsIdAndName();

}
