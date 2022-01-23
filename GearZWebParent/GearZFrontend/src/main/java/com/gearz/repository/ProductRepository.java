package com.gearz.repository;

import java.util.List;

import com.gearz.common.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    public List<Product> findAllByOrderByIdDesc();

    @Query("SELECT p FROM Product p WHERE p.enabled = true AND (p.category.id = ?1 OR p.category.allParentIDs LIKE %?2%) ORDER BY p.id DESC")
    public Page<Product> listByCategory(Integer categoryId, String categoryIDMatch, Pageable pageable);

    @Query(value = "SELECT * FROM products WHERE products.enabled = true AND MATCH(name, short_description, full_description) AGAINST (?1)", nativeQuery = true)
    public Page<Product> search(String keyword, Pageable pageable);

    public Product findByTag(String tag);
}
