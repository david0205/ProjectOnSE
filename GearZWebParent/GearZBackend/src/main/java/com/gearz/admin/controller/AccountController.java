package com.gearz.admin.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gearz.admin.security.UserDetailsSecurity;
import com.gearz.admin.service.UserService;
import com.gearz.admin.utils.FileUploadUtil;
import com.gearz.common.entity.Role;
import com.gearz.common.entity.User;

@Controller
public class AccountController {

	@Autowired
	private UserService service;
	
	@GetMapping("/account")
	public String viewAccountInfo(@AuthenticationPrincipal UserDetailsSecurity signedinUser, Model model) {
		String email = signedinUser.getUsername();
		User user = service.getUserByEmail(email);
		List<Role> roles = service.listAllRoles();
		model.addAttribute("user", user);
		model.addAttribute("roleList", roles);
		return "forms/account";
	}
	
	@PostMapping("/account/update")
	public String saveNewDetails(@RequestParam("image") MultipartFile multipartFile,
			@AuthenticationPrincipal UserDetailsSecurity signedinUser,
			User user, RedirectAttributes rAttributes) throws InterruptedException, IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setProfilePic(fileName);
			User savedUser = service.updateAccount(user);
			
			String uploadDir = "user-profile-picture/" + savedUser.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);			
		} else {
			if (user.getProfilePic().isEmpty()) {
				user.setProfilePic(null);
			}
			service.updateAccount(user);
		}
		signedinUser.setFirstName(user.getFirstName());
		signedinUser.setLastName(user.getLastName());
		
		rAttributes.addFlashAttribute("success", "Details successfully updated!");
		
		return "redirect:/account";
	}
}
