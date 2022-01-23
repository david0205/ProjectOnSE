package com.gearz.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gearz.admin.service.BrandService;
import com.gearz.admin.service.CategoryService;
import com.gearz.admin.service.CustomerService;
import com.gearz.admin.service.OrderService;
import com.gearz.admin.service.ProductService;
import com.gearz.admin.service.SettingService;
import com.gearz.admin.service.ShippingRateService;
import com.gearz.admin.service.UserService;
import com.gearz.common.entity.Brand;
import com.gearz.common.entity.Category;
import com.gearz.common.entity.Customer;
import com.gearz.common.entity.Order;
import com.gearz.common.entity.Product;
import com.gearz.common.entity.Setting;
import com.gearz.common.entity.ShippingRate;
import com.gearz.common.entity.User;

@Controller
public class MainController {

	private UserService userService;
	private CategoryService categoryService;
	private BrandService brandService;
	private ProductService productService;
	private SettingService settingService;
	private CustomerService customerService;
	private ShippingRateService shippingRateService;
	private OrderService orderService;

	@Autowired
	public MainController(UserService userService, CategoryService categoryService, BrandService brandService,
			ProductService productService, SettingService settingService, CustomerService customerService,
			ShippingRateService shippingRateService, OrderService orderService) {
		this.userService = userService;
		this.categoryService = categoryService;
		this.brandService = brandService;
		this.productService = productService;
		this.settingService = settingService;
		this.customerService = customerService;
		this.shippingRateService = shippingRateService;
		this.orderService = orderService;
	}

	@GetMapping("")
	public String homePage(@Param("categoriesSortDir") String categoriesSortDir,
			@Param("categoryId") Integer categoryId, Model model) {

		/* Manage users tab */
		List<User> users = userService.listAllUsers();
		model.addAttribute("listUsers", users);

		/* Manage categories tab */
		if (categoriesSortDir == null || categoriesSortDir.isEmpty()) {
			categoriesSortDir = "asc";
		}
		List<Category> categories = categoryService.listAllCategories(categoriesSortDir);
		model.addAttribute("listCategories", categories);
		model.addAttribute("sortDir", categoriesSortDir.equals("asc") ? "desc" : "asc");

		/* Manage brands tab */
		List<Brand> brands = brandService.listAllBrands();
		model.addAttribute("listBrands", brands);

		/* Manage products tab */
		List<Category> cList = categoryService.listAllCategoriesWithoutHierarchicalStructure();
		List<Product> products = productService.listAllProducts();
		model.addAttribute("listProducts", products);
		model.addAttribute("categoryList", cList);

		/* Manage customers tab */
		List<Customer> customers = customerService.listAllCustomers();
		model.addAttribute("listCustomers", customers);

		/* Manage shipping rates tab */
		List<ShippingRate> shippingRates = shippingRateService.listAllShippingRates();
		model.addAttribute("listShippingRates", shippingRates);

		/* Manage orders tab */
		List<Order> orders = orderService.listAllOrders();
		model.addAttribute("listOrders", orders);

		/* Site setting tab */
		List<Setting> lSettings = settingService.listAllSettings();
		for (Setting setting : lSettings) {
			model.addAttribute(setting.getKey(), setting.getValue());
		}

		return "index";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
}
