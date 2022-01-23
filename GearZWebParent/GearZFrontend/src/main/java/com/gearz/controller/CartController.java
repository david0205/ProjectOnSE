package com.gearz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gearz.Utilities;
import com.gearz.common.entity.Address;
import com.gearz.common.entity.CartItem;
import com.gearz.common.entity.Customer;
import com.gearz.common.entity.ShippingRate;
import com.gearz.service.AddressService;
import com.gearz.service.CartService;
import com.gearz.service.CustomerService;
import com.gearz.service.ShippingRateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ShippingRateService shippingRateService;

    @GetMapping("/cart")
    public String viewCart(HttpServletRequest request, Model model) {
        Customer authenticatedCustomer = getAuthenticatedCustomer(request);
        List<CartItem> cartItems = cartService.listCartItems(authenticatedCustomer);

        if (authenticatedCustomer.getCity() == null || authenticatedCustomer.getDistrict() == null
                || authenticatedCustomer.getWard() == null) {
            model.addAttribute("pageTitle", "Oops!");
            model.addAttribute("message", "It looks like you don't have any address.");
            model.addAttribute("message2",
                    "If this is the first time you login to our website, please add an address in 'My Profile' to continue.");
            return "order_fail";
        }

        Address defaultAddress = addressService.getDefaultAddress(authenticatedCustomer);
        ShippingRate shippingRate = null;
        boolean primaryAddressAsDefault = false;

        if (defaultAddress != null) {
            shippingRate = shippingRateService.getShippingRateForAddress(defaultAddress);
        } else {
            primaryAddressAsDefault = true;
            shippingRate = shippingRateService.getShippingRateForCustomer(authenticatedCustomer);
        }

        model.addAttribute("primaryAddressAsDefault", primaryAddressAsDefault);
        model.addAttribute("shippingSupported", shippingRate != null);
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = Utilities.getEmailOfLoggedInCustomer(request);
        return customerService.getCustomerByEmail(email);
    }
}
