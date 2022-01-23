package com.gearz.repository;

import java.util.List;

import com.gearz.common.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.enabled = true AND c.parent = null ORDER BY c.id ASC")
    public List<Category> findAllEnabledMainCategories();

    @Query("SELECT c FROM Category c WHERE c.enabled = true AND c.tag = ?1")
    public Category findByTagEnabled(String tag);

}
