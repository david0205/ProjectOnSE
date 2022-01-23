package com.gearz.controller.rest;

import com.gearz.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController {

    @Autowired
    private CustomerService service;

    @PostMapping("/customer/check_email_existed")
    public String checkEmailExisted(String email) {
        return service.isEmailExisted(email) ? "OK" : "Existed";
    }
}
