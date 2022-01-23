package com.gearz.controller.rest;

import javax.servlet.http.HttpServletRequest;

import com.gearz.Utilities;
import com.gearz.common.entity.Customer;
import com.gearz.common.exception.CustomerNotFoundException;
import com.gearz.common.exception.OrderNotFoundException;
import com.gearz.dto.OrderReturnRequest;
import com.gearz.dto.OrderReturnResponse;
import com.gearz.service.CustomerService;
import com.gearz.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/orders/return")
    public ResponseEntity<?> handleOrderReturnRequest(@RequestBody OrderReturnRequest returnRequest,
            HttpServletRequest servletRequest) {
        System.out.println(returnRequest.getOrderId());
        System.out.println(returnRequest.getReason());
        System.out.println(returnRequest.getAdditionalInfo());
        Customer customer = null;
        try {
            customer = getAuthenticatedCustomer(servletRequest);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>("Authentication required", HttpStatus.BAD_REQUEST);
        }
        try {
            orderService.setOrderReturnRequested(returnRequest, customer);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new OrderReturnResponse(returnRequest.getOrderId()), HttpStatus.OK);
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utilities.getEmailOfLoggedInCustomer(request);
        if (email == null) {
            throw new CustomerNotFoundException("No logged in user");
        }
        return customerService.getCustomerByEmail(email);
    }
}
