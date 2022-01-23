package com.gearz.admin.controller.rest;

import com.gearz.admin.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RestController
public class OrderRestController {

    @Autowired
    private OrderService service;

    @PostMapping("/orders_shipper/update/{id}/{status}")
    public Response updateOrderStatus(@PathVariable("id") Integer orderId, @PathVariable("status") String status) {
        service.updateStatus(orderId, status);
        return new Response(orderId, status);
    }
}

/**
 * Response
 */
@Getter
@Setter
@AllArgsConstructor
class Response {

    private Integer orderId;
    private String status;
}