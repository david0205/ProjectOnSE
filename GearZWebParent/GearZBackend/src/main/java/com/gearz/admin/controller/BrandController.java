package com.gearz.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gearz.admin.exceptions.BrandNotFoundException;
import com.gearz.admin.service.BrandService;
import com.gearz.admin.service.CategoryService;
import com.gearz.admin.utils.FileUploadUtil;
import com.gearz.common.entity.Brand;
import com.gearz.common.entity.Category;

@Controller
public class BrandController {

	private String link = "";

	@Autowired
	private BrandService bService;

	@Autowired
	private CategoryService cService;

	@GetMapping("/brands/new")
	public String addNewBrand(Model model) {
		List<Category> categoryList = cService.categoryDropDownList();

		model.addAttribute("brand", new Brand());
		model.addAttribute("listCategories", categoryList);
		model.addAttribute("pageTitle", "Add new brand");

		return "forms/brand";
	}

	@GetMapping("/brands/edit/{id}")
	public String editBrand(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes rAttributes,
			HttpServletRequest httpServletRequest) throws BrandNotFoundException {
		link = httpServletRequest.getRequestURI();
		try {
			Brand brand = bService.getBrandById(id);
			List<Category> listCategories = cService.categoryDropDownList();

			model.addAttribute("brand", brand);
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("pageTitle", "Edit brand (ID: " + id + ")");

			return "forms/brand";
		} catch (BrandNotFoundException e) {
			rAttributes.addFlashAttribute("msg", e.getMessage());
			return "redirect:/";
		}
	}

	@PostMapping("/brands/save")
	public String saveBrand(@RequestParam("imageFile") MultipartFile multipartFile, Brand brand,
			RedirectAttributes rAttributes) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			brand.setLogo(fileName);

			Brand savedBrand = bService.saveBrand(brand);

			String uploadDir = "../brand-logos/" + savedBrand.getId();

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			bService.saveBrand(brand);
		}

		rAttributes.addFlashAttribute("success", "Brand saved successfully");

		if (link.contains("/edit/")) {
			return "redirect:/brands/edit/" + brand.getId();
		}

		return "redirect:/brands/new";
	}

	@GetMapping("/brands/delete/{id}")
	public String deleteCategory(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes rAttributes) {
		try {
			bService.deleteBrand(id);
			FileUploadUtil.removeDir("../brand-logos/" + id);
			rAttributes.addFlashAttribute("msg", "The brand ID " + id + " has been deleted");
		} catch (BrandNotFoundException e) {
			rAttributes.addFlashAttribute("msg", e.getMessage());
		}

		return "redirect:/";
	}
}
