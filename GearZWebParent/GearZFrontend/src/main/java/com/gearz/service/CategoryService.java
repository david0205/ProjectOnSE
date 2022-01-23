package com.gearz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.gearz.common.entity.Category;
import com.gearz.common.exception.CategoryNotFoundException;
import com.gearz.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public List<Category> listMainCategories() {
        List<Category> allMainCategories = new ArrayList<>();
        List<Category> enabledCategories = repo.findAllEnabledMainCategories();

        enabledCategories.forEach(category -> {
            allMainCategories.add(category);
        });

        return allMainCategories;
    }

    public List<Category> listSubCategories() {
        List<Category> subCategories = new ArrayList<>();
        List<Category> enabledCategories = repo.findAllEnabledMainCategories();

        enabledCategories.forEach(category -> {
            Set<Category> children = category.getChildren();
            if (children != null) {
                children.forEach(cat -> {
                    subCategories.add(cat);
                });
            }
        });

        return subCategories;
    }

    public Category getCategory(String tag) throws CategoryNotFoundException {
        Category category = repo.findByTagEnabled(tag);
        if (category == null) {
            throw new CategoryNotFoundException("This category does not exist!");
        }
        return category;
    }

    public List<Category> getCategoryParents(Category child) {
        List<Category> parentCategories = new ArrayList<>();
        Category parent = child.getParent();

        while (parent != null) {
            parentCategories.add(0, parent);
            parent = parent.getParent();
        }

        parentCategories.add(child);
        return parentCategories;
    }
}
