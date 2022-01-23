package com.gearz.admin.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import com.gearz.admin.repository.ProductRepository;
import com.gearz.common.entity.Product;
import com.gearz.common.exception.ProductNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public List<Product> listAllProducts() {
        return (List<Product>) repo.findAll();
    }

    public Product saveProduct(Product product) {
        if (product.getId() == null) {
            product.setCreatedTime(new Date());
        }
        if (product.getTag() == null || product.getTag().isEmpty()) {
            String defaultTag = product.getName().replace(" ", "-");
            product.setTag(defaultTag);
        } else {
            product.setTag(product.getTag().replace(" ", "-"));
        }
        product.setUpdatedTime(new Date());
        return repo.save(product);
    }

    public void saveProductPrice(Product priceUpdatedProduct) {
        Product product = repo.findById(priceUpdatedProduct.getId()).get();
        product.setPrice(priceUpdatedProduct.getPrice());
        product.setDiscountPercentage(priceUpdatedProduct.getDiscountPercentage());
        repo.save(product);
    }

    public String checkExisted(Integer id, String name) {
        boolean isNew = (id == null || id == 0);
        Product productFound = repo.findByName(name);

        if (isNew) {
            if (productFound != null) {
                return "Exist";
            }
        } else {
            if (productFound != null && productFound.getId() != id) {
                return "Exist";
            }
        }

        return "OK";
    }

    public void updateProductStatus(Integer id, boolean enabled) {
        repo.updateProductStatus(id, enabled);
    }

    public void deleteProduct(Integer id) throws ProductNotFoundException {
        Long countById = repo.countById(id);
        if (countById == null || countById == 0) {
            throw new ProductNotFoundException("Could not find any category with ID " + id);
        }
        repo.deleteById(id);
    }

    public Product getProductById(Integer id) throws ProductNotFoundException {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException("Could not find any product with ID " + id);
        }
    }
}
