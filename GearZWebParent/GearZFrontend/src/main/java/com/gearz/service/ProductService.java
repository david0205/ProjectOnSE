package com.gearz.service;

import java.util.List;

import com.gearz.common.entity.Product;
import com.gearz.common.exception.ProductNotFoundException;
import com.gearz.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public static final int PRODUCT_PER_PAGE = 8;

    @Autowired
    private ProductRepository repo;

    public List<Product> listNewestToOldest() {
        return repo.findAllByOrderByIdDesc();
    }

    public Page<Product> listByCategory(int pageNum, Integer categoryId) {
        String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCT_PER_PAGE);

        return repo.listByCategory(categoryId, categoryIdMatch, pageable);
    }

    public Page<Product> search(String keyword, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCT_PER_PAGE);
        return repo.search(keyword, pageable);
    }

    public Product getProduct(String tag) throws ProductNotFoundException {
        Product product = repo.findByTag(tag);
        if (product == null) {
            throw new ProductNotFoundException("This product does not exist!");
        }
        return product;
    }

    public Product getProductById(Integer id) throws ProductNotFoundException {
        Product product = repo.findById(id).get();
        if (product == null) {
            throw new ProductNotFoundException("This product does not exist!");
        }
        return product;
    }

}
