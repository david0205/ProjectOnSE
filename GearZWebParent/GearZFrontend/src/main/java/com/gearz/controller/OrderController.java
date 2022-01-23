package com.gearz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gearz.Utilities;
import com.gearz.common.entity.Customer;
import com.gearz.common.entity.Order;
import com.gearz.service.CustomerService;
import com.gearz.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/orders")
    public String listAllOrders(Model model, HttpServletRequest request) {
        Customer customer = getAuthenticatedCustomer(request);
        List<Order> orders = orderService.listAllOrderForCustomer(customer);

        model.addAttribute("orders", orders);
        return "track_order";
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = Utilities.getEmailOfLoggedInCustomer(request);
        return customerService.getCustomerByEmail(email);
    }

    @GetMapping("/orders/detail/{id}")
    public String viewOrderDetails(Model model, @PathVariable("id") Integer id, HttpServletRequest request) {
        Customer customer = getAuthenticatedCustomer(request);
        Order order = orderService.getOrder(id, customer);
        model.addAttribute("order", order);
        return "forms/order_details";
    }
}
