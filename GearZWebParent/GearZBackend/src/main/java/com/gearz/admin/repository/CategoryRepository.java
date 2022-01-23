package com.gearz.admin.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gearz.common.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public List<Category> getRootCategories(Sort sort);
	
	public Category findByName(String name);
	
	public Category findByTag(String tag);
	
	@Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id = ?1")
	@Modifying
	public void updateCategoryStatus(Integer id, boolean enabled);
	
	public Long countById(Integer id);
}
