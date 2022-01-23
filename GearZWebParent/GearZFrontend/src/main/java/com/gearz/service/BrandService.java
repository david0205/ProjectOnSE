package com.gearz.service;

import java.util.List;

import com.gearz.common.entity.Brand;
import com.gearz.repository.BrandRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

	@Autowired
	private BrandRepository repo;

	public List<Brand> listAllBrands() {
		return (List<Brand>) repo.findAll();
	}
}
