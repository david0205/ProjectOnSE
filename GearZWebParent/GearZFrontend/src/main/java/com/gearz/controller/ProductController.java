package com.gearz.controller;

import java.util.List;

import com.gearz.common.entity.Category;
import com.gearz.common.entity.Product;
import com.gearz.common.exception.CategoryNotFoundException;
import com.gearz.service.CategoryService;
import com.gearz.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/c/{category_tag}")
    public String viewCategoryFirstPage(@PathVariable("category_tag") String tag, Model model) {
        return viewCategoryByPage(tag, 1, model);
    }

    @GetMapping("/c/{category_tag}/page/{pageNum}")
    public String viewCategoryByPage(@PathVariable("category_tag") String tag, @PathVariable("pageNum") int pageNum,
            Model model) {
        try {
            Category category = categoryService.getCategory(tag);

            List<Category> parentCategories = categoryService.getCategoryParents(category);
            model.addAttribute("pageTitle", category.getName());
            model.addAttribute("parentCategories", parentCategories);
            model.addAttribute("imagePath", category.getImagePath());

            Page<Product> pageProducts = productService.listByCategory(pageNum, category.getId());
            List<Product> products = pageProducts.getContent();

            long start = (pageNum - 1) * ProductService.PRODUCT_PER_PAGE + 1;
            long end = start + ProductService.PRODUCT_PER_PAGE - 1;
            if (end > pageProducts.getTotalElements()) {
                end = pageProducts.getTotalElements();
            }

            model.addAttribute("currentPage", pageNum);
            model.addAttribute("totalPages", pageProducts.getTotalPages());
            model.addAttribute("start", start);
            model.addAttribute("end", end);
            model.addAttribute("totalItems", pageProducts.getTotalElements());
            model.addAttribute("products", products);
            model.addAttribute("category", category);

            return "product_listing_grid";
        } catch (CategoryNotFoundException e) {
            return "error/404";
        }
    }

    @GetMapping("/products/{product_tag}")
    public String productDetail(@PathVariable("product_tag") String tag, Model model) {
        try {
            Product product = productService.getProduct(tag);

            List<Category> parentCategories = categoryService.getCategoryParents(product.getCategory());

            model.addAttribute("pageTitle", product.getName());
            model.addAttribute("parentCategories", parentCategories);
            model.addAttribute("product", product);

            return "product_detail";
        } catch (Exception e) {
            return "error/404";
        }
    }

    @GetMapping("/search")
    public String searchFirstPage(@Param("keyword") String keyword, Model model) {
        return searchByPage(keyword, 1, model);
    }

    @GetMapping("/search/page/{pageNum}")
    public String searchByPage(@Param("keyword") String keyword, @PathVariable("pageNum") int pageNum, Model model) {
        Page<Product> pageProducts = productService.search(keyword, pageNum);
        List<Product> results = pageProducts.getContent();

        long start = (pageNum - 1) * ProductService.PRODUCT_PER_PAGE + 1;
        long end = start + ProductService.PRODUCT_PER_PAGE - 1;
        if (end > pageProducts.getTotalElements()) {
            end = pageProducts.getTotalElements();
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", pageProducts.getTotalPages());
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("totalItems", pageProducts.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("results", results);
        return "search_results";
    }
}
