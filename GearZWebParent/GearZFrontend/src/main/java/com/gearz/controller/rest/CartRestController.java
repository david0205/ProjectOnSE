package com.gearz.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gearz.Utilities;
import com.gearz.common.entity.CartItem;
import com.gearz.common.entity.Customer;
import com.gearz.common.entity.Product;
import com.gearz.common.exception.CustomerNotFoundException;
import com.gearz.common.exception.ProductNotFoundException;
import com.gearz.dto.CartItemDTO;
import com.gearz.service.CartService;
import com.gearz.service.CustomerService;
import com.gearz.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartRestController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @PostMapping("/cart/add/{productId}/{quantity}")
    public String addProductToCart(@PathVariable("productId") Integer productId,
            @PathVariable("quantity") Integer quantity, HttpServletRequest request) {
        try {
            Customer authenticatedCustomer = getAuthenticatedCustomer(request);
            Product productById = productService.getProductById(productId);
            Integer updatedQuantity = cartService.addProduct(productId, authenticatedCustomer, quantity);
            return updatedQuantity + " item(s) of \"" + productById.getName() + "\" were added to your cart";
        } catch (CustomerNotFoundException e) {
            return "You must log in to add this product to cart";
        } catch (ProductNotFoundException e) {
            return e.getMessage();
        }
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utilities.getEmailOfLoggedInCustomer(request);
        if (email == null) {
            throw new CustomerNotFoundException("No logged in user");
        }
        return customerService.getCustomerByEmail(email);
    }

    @PostMapping("/cart/update/{productId}/{quantity}")
    public String updateQuantity(@PathVariable("productId") Integer productId,
            @PathVariable("quantity") Integer quantity, HttpServletRequest request) {
        try {
            Customer authenticatedCustomer = getAuthenticatedCustomer(request);
            float subtotal = cartService.updateQuantity(productId, authenticatedCustomer, quantity);
            return String.valueOf(subtotal);
        } catch (CustomerNotFoundException e) {
            return "You must log in.";
        }
    }

    @DeleteMapping("/cart/remove/{productId}")
    public String removeProduct(@PathVariable("productId") Integer productId, HttpServletRequest request) {
        try {
            Customer customer = getAuthenticatedCustomer(request);
            Product productById = productService.getProductById(productId);

            cartService.removeProductFromCart(productId, customer);
            return productById.getName() + " has been removed from your cart.";
        } catch (CustomerNotFoundException e) {
            return "You must log in.";
        } catch (ProductNotFoundException e) {
            return "This product is not in your cart.";
        }
    }

    @GetMapping("/amount")
    public Integer getCartItemAmount(HttpServletRequest request) {
        String email = Utilities.getEmailOfLoggedInCustomer(request);
        Customer customer = customerService.getCustomerByEmail(email);
        List<CartItem> cartItems = cartService.listCartItems(customer);
        return cartItems.size();
    }

    @GetMapping("/cart-items")
    public List<CartItemDTO> listAllItemsInCart(HttpServletRequest request) {
        String email = Utilities.getEmailOfLoggedInCustomer(request);
        Customer customer = customerService.getCustomerByEmail(email);
        List<CartItem> cartItems = cartService.listCartItems(customer);
        List<CartItemDTO> items = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            items.add(new CartItemDTO(cartItem.getProduct().getId(), cartItem.getProduct().getName(),
                    cartItem.getProduct().getMainImagePath(), cartItem.getProduct().getDiscountedPrice(),
                    cartItem.getQuantity(), cartItem.getSubtotal()));
        }

        return items;
    }
}
