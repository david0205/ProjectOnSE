package com.gearz.admin.controller;

import com.gearz.admin.service.CustomerService;
import com.gearz.common.entity.Customer;
import com.gearz.common.exception.CustomerNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("/customers/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes rAttributes) {
        try {
            service.delete(id);
            rAttributes.addFlashAttribute("msg", "Customer ID " + id + " has been deleted successfully");
        } catch (CustomerNotFoundException e) {
            rAttributes.addFlashAttribute("msg", e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/customers/{id}/enabled/{status}")
    public String changeCustomerEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
            RedirectAttributes rAttributes) {
        service.changeCustomerEnabledStatus(id, enabled);
        String enabledStatus = enabled ? "enabled" : "disabled";
        String msg = "The customer ID " + id + " has been " + enabledStatus;
        rAttributes.addFlashAttribute("msg", msg);
        return "redirect:/";
    }

    @GetMapping("/customers/details/{id}")
    public String viewCustomerDetails(@PathVariable("id") Integer id, Model model, RedirectAttributes rAttributes) {
        try {
            Customer customer = service.get(id);
            model.addAttribute("customer", customer);
            return "fake-modals/customer_details_modal";
        } catch (CustomerNotFoundException e) {
            rAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:/";
        }
    }
}
