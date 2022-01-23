package com.gearz.admin.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gearz.admin.service.CategoryService;

@RestController
public class CategoryRestController {
	
	@Autowired
	private CategoryService service;
	
	@PostMapping("/categories/check_category")
	public String checkExistedCategory(@Param("id") Integer id, @Param("name") String name, @Param("tag") String tag) {
		return service.isCategoryExisted(id, name, tag);
	}
}
