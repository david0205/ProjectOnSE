package com.gearz.controller;

import java.util.List;

import com.gearz.common.entity.Brand;
import com.gearz.common.entity.Product;
import com.gearz.service.BrandService;
import com.gearz.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductService productService;

	@GetMapping("")
	public String homePage(Model model) {
		List<Brand> brands = brandService.listAllBrands();
		model.addAttribute("listBrands", brands);

		List<Product> products = productService.listNewestToOldest();
		model.addAttribute("products", products);

		return "index";
	}
}
