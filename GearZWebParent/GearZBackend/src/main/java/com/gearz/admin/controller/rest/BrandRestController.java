package com.gearz.admin.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.gearz.admin.dto.CategoryDTO;
import com.gearz.admin.exceptions.BrandNotFoundException;
import com.gearz.admin.exceptions.BrandNotFoundRestException;
import com.gearz.admin.service.BrandService;
import com.gearz.common.entity.Brand;
import com.gearz.common.entity.Category;

@RestController
public class BrandRestController {

	@Autowired
	private BrandService service;

	@PostMapping("/brands/check_brand")
	public String checkExist(Integer id, String name) {
		return service.checkExist(id, name);
	}

	@GetMapping("/brands/{id}/categories")
	public List<CategoryDTO> lCategoryByBrand(@PathVariable(name = "id") Integer brandId)
			throws BrandNotFoundRestException {
		List<CategoryDTO> lCategories = new ArrayList<>();
		try {
			Brand brand = service.getBrandById(brandId);
			Set<Category> categories = brand.getCategories();

			for (Category category : categories) {
				CategoryDTO dto = new CategoryDTO(category.getId(), category.getName());
				lCategories.add(dto);
			}

			return lCategories;
		} catch (BrandNotFoundException e) {
			throw new BrandNotFoundRestException();
		}
	}
}
