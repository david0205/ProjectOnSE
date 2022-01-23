package com.gearz;

import java.util.List;

import com.gearz.common.entity.Category;
import com.gearz.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("mainCategories")
    public List<Category> getMainCategories() {
        List<Category> mainCategories = categoryService.listMainCategories();
        return mainCategories;
    }

    @ModelAttribute("subCategories")
    public List<Category> getSubCategories() {
        List<Category> subCategories = categoryService.listSubCategories();
        return subCategories;
    }

}
