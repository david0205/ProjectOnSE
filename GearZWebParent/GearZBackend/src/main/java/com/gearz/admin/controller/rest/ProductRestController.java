package com.gearz.admin.controller.rest;

import com.gearz.admin.dto.ProductDTO;
import com.gearz.admin.service.ProductService;
import com.gearz.common.entity.Product;
import com.gearz.common.exception.ProductNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {

    @Autowired
    private ProductService service;

    @PostMapping("/products/check_product")
    public String checkExisted(@Param("id") Integer id, @Param("name") String name) {
        return service.checkExisted(id, name);
    }

    @GetMapping("/products/get/{id}")
    public ProductDTO getProductInfo(@PathVariable("id") Integer id) throws ProductNotFoundException {
        Product product = service.getProductById(id);
        float productCost = (product.getDiscountedPrice() * 95) / 100;
        return new ProductDTO(product.getName(), product.getMainImagePath(), product.getDiscountedPrice(), productCost);
    }
}
