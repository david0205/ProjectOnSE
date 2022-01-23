package com.gearz.admin.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gearz.admin.service.UserService;

@RestController
public class UserRestController {

	@Autowired
	private UserService service;

	@PostMapping("/users/check_email")
	public String checkExistedEmail(Integer id, String email) {
		return service.isEmailExisted(id, email) ? "OK" : "Exist";
	}
}
