package com.gearz.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import com.gearz.admin.exceptions.UserNotFoundException;
import com.gearz.admin.exporter.UserCSVExporter;
import com.gearz.admin.exporter.UserExcelExporter;
import com.gearz.admin.exporter.UserPDFExporter;
import com.gearz.admin.service.UserService;
import com.gearz.admin.utils.FileUploadUtil;
import com.gearz.common.entity.Role;
import com.gearz.common.entity.User;

@Controller
public class UserController {

	private String link = "";

	@Autowired
	private UserService service;

	@GetMapping("/users/new")
	public String addNewUser(HttpServletRequest httpServletRequest, Model model) {
		link = httpServletRequest.getRequestURI();
		System.out.println(link);
		User user = new User();
		user.setEnabled(true);
		List<Role> roles = service.listAllRoles();
		model.addAttribute("user", user);
		model.addAttribute("roleList", roles);
		model.addAttribute("pageTitle", "Add new user");
		return "forms/user";
	}

	@GetMapping("/users/edit/{id}")
	public String editUser(HttpServletRequest httpServletRequest, @PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes rAttributes) {
		link = httpServletRequest.getRequestURI();
		try {
			User user = service.getUserById(id);
			List<Role> roles = service.listAllRoles();
			model.addAttribute("user", user);
			model.addAttribute("roleList", roles);
			model.addAttribute("pageTitle", "Edit user (ID: " + id + ")");
			return "forms/user";
		} catch (UserNotFoundException e) {
			rAttributes.addFlashAttribute("msg", e.getMessage());
			return "redirect:/";
		}
	}

	@PostMapping("/users/save")
	public String saveNewUser(@RequestParam("image") MultipartFile multipartFile, User user,
			RedirectAttributes rAttributes) throws InterruptedException, IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setProfilePic(fileName);
			User savedUser = service.save(user);

			String uploadDir = "user-profile-picture/" + savedUser.getId();

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			if (user.getProfilePic().isEmpty()) {
				user.setProfilePic(null);
			}
			service.save(user);
		}
		rAttributes.addFlashAttribute("success", "User saved successfully!");

		if (link.contains("/edit/")) {
			return "redirect:/users/edit/" + user.getId();
		}
		return "redirect:/users/new";
	}

	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes rAttributes) {
		try {
			service.delete(id);
			FileUploadUtil.removeDir("user-profile-picture/" + id);
			rAttributes.addFlashAttribute("msg", "User ID " + id + " has been deleted successfully");
		} catch (UserNotFoundException e) {
			rAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/";
	}

	@GetMapping("/users/{id}/enabled/{status}")
	public String changeUserEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
			RedirectAttributes rAttributes) {
		service.changeUserEnabledStatus(id, enabled);
		String enabledStatus = enabled ? "enabled" : "disabled";
		String msg = "The user ID " + id + " has been " + enabledStatus;
		rAttributes.addFlashAttribute("msg", msg);
		return "redirect:/";
	}

	@GetMapping("/users/export/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException {
		List<User> listAllUsers = service.listAllUsers();
		UserCSVExporter csvExporter = new UserCSVExporter();
		csvExporter.export(listAllUsers, response);
	}

	@GetMapping("/users/export/xlsx")
	public void exportToXLSX(HttpServletResponse response) throws IOException {
		List<User> listAllUsers = service.listAllUsers();
		UserExcelExporter excelExporter = new UserExcelExporter();
		excelExporter.export(listAllUsers, response);
	}

	@GetMapping("/users/export/pdf")
	public void exportToPDF(HttpServletResponse response) throws IOException {
		List<User> listAllUsers = service.listAllUsers();
		UserPDFExporter excelExporter = new UserPDFExporter();
		excelExporter.export(listAllUsers, response);
	}

}
