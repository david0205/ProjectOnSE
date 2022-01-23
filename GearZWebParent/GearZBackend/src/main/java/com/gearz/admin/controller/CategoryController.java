package com.gearz.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.gearz.admin.exporter.CategoryCSVExporter;
import com.gearz.admin.service.CategoryService;
import com.gearz.admin.utils.FileUploadUtil;
import com.gearz.common.entity.Category;
import com.gearz.common.exception.CategoryNotFoundException;

@Controller
public class CategoryController {

	private String link = "";

	@Autowired
	private CategoryService service;

	@GetMapping("/categories/new")
	public String addNewCategory(Model model) {
		List<Category> categories = service.categoryDropDownList();

		model.addAttribute("category", new Category());
		model.addAttribute("listCategories", categories);
		model.addAttribute("pageTitle", "Add new category");

		return "forms/category";
	}

	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes rAttributes,
			HttpServletRequest httpServletRequest) {
		link = httpServletRequest.getRequestURI();
		try {
			Category category = service.getCategoryById(id);
			List<Category> listCategories = service.categoryDropDownList();

			model.addAttribute("category", category);
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("pageTitle", "Edit category (ID: " + id + ")");

			return "forms/category";
		} catch (CategoryNotFoundException e) {
			rAttributes.addFlashAttribute("msg", e.getMessage());
			return "redirect:/";
		}
	}

	@PostMapping("/categories/save")
	public String saveCategory(@RequestParam("imageFile") MultipartFile multipartFile, RedirectAttributes rAttributes,
			Category category) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		category.setImage(fileName);

		Category savedCategory = service.saveCategory(category);
		String uploadDir = "../category-images/" + savedCategory.getId();

		FileUploadUtil.cleanDir(uploadDir);
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		rAttributes.addFlashAttribute("success", "Category saved successfully");

		if (link.contains("/edit/")) {
			return "redirect:/categories/edit/" + category.getId();
		}
		return "redirect:/categories/new";
	}

	@GetMapping("/categories/{id}/enabled/{status}")
	public String updateCategoryStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
			RedirectAttributes rAttributes) {
		service.updateCategoryEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		rAttributes.addFlashAttribute("msg", "Category ID " + id + " has been " + status);
		return "redirect:/";
	}

	@GetMapping("/categories/delete/{id}")
	public String deleteCategory(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes rAttributes) {
		try {
			service.deleteCategory(id);
			FileUploadUtil.removeDir("../category-images/" + id);
			rAttributes.addFlashAttribute("msg", "The category ID " + id + " has been deleted");
		} catch (CategoryNotFoundException e) {
			rAttributes.addFlashAttribute("msg", e.getMessage());
		}

		return "redirect:/";
	}

	@GetMapping("/categories/export/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException {
		List<Category> categoriesList = service.categoryDropDownList();
		CategoryCSVExporter exporter = new CategoryCSVExporter();
		exporter.export(categoriesList, response);
	}
}
