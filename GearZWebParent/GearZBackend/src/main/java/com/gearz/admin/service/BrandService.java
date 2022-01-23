package com.gearz.admin.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gearz.admin.exceptions.BrandNotFoundException;
import com.gearz.admin.repository.BrandRepository;
import com.gearz.common.entity.Brand;

@Service
@Transactional
public class BrandService {

	@Autowired
	private BrandRepository repo;

	public List<Brand> listAllBrands() {
		return (List<Brand>) repo.findAll();
	}

	public List<Brand> listAllBrandsIdAndName() {
		return (List<Brand>) repo.findAllBrandsIdAndName();
	}

	public Brand saveBrand(Brand brand) {
		return repo.save(brand);
	}

	public Brand getBrandById(Integer id) throws BrandNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new BrandNotFoundException("No brand ID " + id + " was found");
		}
	}

	public void deleteBrand(Integer id) throws BrandNotFoundException {
		Long countById = repo.countById(id);
		if (countById == null || countById == 0) {
			throw new BrandNotFoundException("Could not find any category with ID " + id);
		}
		repo.deleteById(id);
	}

	public String checkExist(Integer id, String name) {
		boolean isNew = (id == null || id == 0);
		Brand brandName = repo.findByName(name);

		if (isNew) {
			if (brandName != null) {
				return "Exist";
			}
		} else {
			if (brandName != null && brandName.getId() != id) {
				return "Exist";
			}
		}
		return "OK";
	}
}
