package com.gearz.admin.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.gearz.admin.security.UserDetailsSecurity;
import com.gearz.admin.service.BrandService;
import com.gearz.admin.service.ProductService;
import com.gearz.admin.utils.FileUploadUtil;
import com.gearz.common.entity.Brand;
import com.gearz.common.entity.Product;
import com.gearz.common.entity.ProductImage;
import com.gearz.common.exception.ProductNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductController {

    private static final Logger logger = LogManager.getLogger(ProductController.class);

    private String link = "";

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @GetMapping("/products/new")
    public String addNewProduct(Model model) {
        List<Brand> lBrands = brandService.listAllBrandsIdAndName();

        Product product = new Product();
        product.setEnabled(true);
        product.setInStock(true);

        model.addAttribute("product", product);
        model.addAttribute("listBrands", lBrands);
        model.addAttribute("pageTitle", "Add new product");
        model.addAttribute("existingExtraImagesCount", 0);

        return "forms/product";
    }

    @GetMapping("/products/{id}/enabled/{status}")
    public String updateProductStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
            RedirectAttributes rAttributes) {
        productService.updateProductStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        rAttributes.addFlashAttribute("msg", "Product ID " + id + " has been " + status);
        return "redirect:/";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes rAttributes) {
        try {
            productService.deleteProduct(id);
            String productImagesDir = "../product-images/" + id;
            String productExtraImagesDir = "../product-images/" + id + "/extra-images";

            FileUploadUtil.removeDir(productExtraImagesDir);
            FileUploadUtil.removeDir(productImagesDir);

            rAttributes.addFlashAttribute("msg", "The product ID " + id + " has been deleted");
        } catch (ProductNotFoundException e) {
            rAttributes.addFlashAttribute("msg", e.getMessage());
        }

        return "redirect:/";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model, RedirectAttributes rAttributes,
            HttpServletRequest httpServletRequest) throws ProductNotFoundException {
        link = httpServletRequest.getRequestURI();
        try {
            Product product = productService.getProductById(id);
            List<Brand> lBrands = brandService.listAllBrandsIdAndName();
            Integer existingExtraImagesCount = product.getImages().size();

            model.addAttribute("product", product);
            model.addAttribute("listBrands", lBrands);
            model.addAttribute("pageTitle", "Edit product (ID: " + id + ")");
            model.addAttribute("existingExtraImagesCount", existingExtraImagesCount);

            return "forms/product";
        } catch (ProductNotFoundException e) {
            rAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/products/details/{id}")
    public String viewProductDetails(@PathVariable("id") Integer id, Model model, RedirectAttributes rAttributes)
            throws ProductNotFoundException {
        try {
            Product product = productService.getProductById(id);
            model.addAttribute("product", product);
            model.addAttribute("pageTitle", "Product details: " + product.getName());

            return "fake-modals/product_details";
        } catch (ProductNotFoundException e) {
            rAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/products/save")
    public String saveProduct(Product product, RedirectAttributes rAttributes,
            @RequestParam(value = "imageFile", required = false) MultipartFile mainImageMultipartFile,
            @RequestParam(value = "extraImage", required = false) MultipartFile[] extraImageMultipartFiles,
            @RequestParam(name = "specIDs", required = false) String[] specificationIDs,
            @RequestParam(name = "specName", required = false) String[] specificationNames,
            @RequestParam(name = "specValue", required = false) String[] specificationValues,
            @RequestParam(name = "extraSpecDetailCount", required = false) Integer extraSpecDetailCount,
            @RequestParam(name = "imageIDs", required = false) String[] imageIDs,
            @RequestParam(name = "imageNames", required = false) String[] imageNames,
            @AuthenticationPrincipal UserDetailsSecurity loggedUser) throws IOException {

        if (loggedUser.hasRole("Salesperson")) {
            productService.saveProductPrice(product);
            rAttributes.addFlashAttribute("success", "Product saved successfully");

            return "redirect:/products/edit/" + product.getId();
        }

        setMainImageFileName(mainImageMultipartFile, product);
        setExistingExtraImageFileNames(imageIDs, imageNames, product);
        setNewExtraImageFileName(extraImageMultipartFiles, product);
        setProductSpecifications(extraSpecDetailCount, specificationIDs, specificationNames, specificationValues,
                product);

        Product savedProduct = productService.saveProduct(product);
        saveUploadedImages(mainImageMultipartFile, extraImageMultipartFiles, savedProduct);

        removeRemainedExtraImages(product);

        rAttributes.addFlashAttribute("success", "Product saved successfully");

        if (link.contains("/edit/")) {
            return "redirect:/products/edit/" + product.getId();
        }

        return "redirect:/products/new";
    }

    // This is function is for delete the remained extra image(s) that were removed
    // from database
    private void removeRemainedExtraImages(Product product) {
        String extraImage = "../product-images/" + product.getId() + "/extra-images";
        Path path = Paths.get(extraImage);
        try {
            Files.list(path).forEach(imageFile -> {
                String imageFileName = imageFile.toFile().getName();
                if (!product.containsImageName(imageFileName)) {
                    try {
                        Files.delete(imageFile);
                        logger.info("Deleted extra image: " + imageFileName);
                    } catch (IOException e) {
                        logger.error("Could not delete extra image: " + imageFileName);
                    }
                }
            });
        } catch (IOException e) {
            logger.error("Could not list directory: " + path);
        }
    }

    private void setExistingExtraImageFileNames(String[] imageIDs, String[] imageNames, Product product) {
        if (imageIDs == null || imageIDs.length == 0) {
            return;
        }
        Set<ProductImage> images = new HashSet<>();
        for (int count = 0; count < imageIDs.length; count++) {
            Integer id = Integer.parseInt(imageIDs[count]);
            String imageFileName = imageNames[count];
            images.add(new ProductImage(id, imageFileName, product));
        }
        product.setImages(images);
    }

    private void setProductSpecifications(Integer extraSpecDetailCount, String[] specificationIds,
            String[] specificationNames, String[] specificationValues, Product product) {
        String[] specsIDCopy = new String[extraSpecDetailCount + 1]; // The specificationIds length assigned to
                                                                     // its first load stage and stuck it there.
                                                                     // So i had to work around like this.
                                                                     // I could've done the same with extra images
                                                                     // method
        for (int i = 0; i < specificationIds.length; i++) {
            specsIDCopy[i] = specificationIds[i];
        }
        for (int i = specificationIds.length; i < extraSpecDetailCount; i++) {
            specsIDCopy[i] = "0";
        }

        if (specificationNames == null || specificationNames.length == 0) {
            return;
        }
        if (specificationValues == null || specificationValues.length == 0) {
            return;
        }
        for (int count = 0; count < specificationNames.length; count++) {
            String name = specificationNames[count];
            String value = specificationValues[count];
            Integer id = Integer.parseInt(specsIDCopy[count]);
            if (id != 0) { // Specification detail already exists
                product.addSpecifications(id, name, value);
            } else if (!name.isEmpty() && !value.isEmpty()) {
                product.addSpecifications(name, value);
            }
        }
    }

    private void saveUploadedImages(MultipartFile mainImageMultipartFile, MultipartFile[] extraImageMultipartFiles,
            Product savedProduct) throws IOException {
        if (!mainImageMultipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(mainImageMultipartFile.getOriginalFilename());
            String uploadDir = "../product-images/" + savedProduct.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, mainImageMultipartFile);
        }

        if (extraImageMultipartFiles.length > 0) {
            String uploadDir = "../product-images/" + savedProduct.getId() + "/extra-images";
            for (MultipartFile multipartFile : extraImageMultipartFiles) {
                if (multipartFile.isEmpty())
                    continue;
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }
        }

    }

    private void setNewExtraImageFileName(MultipartFile[] extraImageMultipartFiles, Product product) {
        if (extraImageMultipartFiles.length > 0) {
            for (MultipartFile multipartFile : extraImageMultipartFiles) {
                if (!multipartFile.isEmpty()) {
                    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                    if (!product.containsImageName(fileName)) {
                        product.addExtraImage(fileName);
                    }
                }
            }
        }
    }

    private void setMainImageFileName(MultipartFile mainImageMultipartFile, Product product) {
        if (!mainImageMultipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(mainImageMultipartFile.getOriginalFilename());
            product.setMainImage(fileName);
        }
    }
}
