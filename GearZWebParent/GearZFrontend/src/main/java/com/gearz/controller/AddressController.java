package com.gearz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gearz.Utilities;
import com.gearz.common.entity.Address;
import com.gearz.common.entity.City;
import com.gearz.common.entity.Customer;
import com.gearz.common.entity.District;
import com.gearz.common.entity.Ward;
import com.gearz.service.AddressService;
import com.gearz.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/address_book")
    public String showAddressBook(HttpServletRequest request, Model model) {
        Customer customer = getAuthenticatedCustomer(request);
        List<Address> listAddresses = addressService.listAddresses(customer);

        boolean primaryAddressAsDefault = true;
        for (Address address : listAddresses) {
            if (address.isDefaultForShipping()) {
                primaryAddressAsDefault = false;
                break;
            }
        }

        if (customer.getCity() == null || customer.getDistrict() == null
                || customer.getWard() == null) {
            model.addAttribute("pageTitle", "Oops!");
            model.addAttribute("message", "It looks like you don't have any address.");
            model.addAttribute("message2",
                    "If this is the first time you login to our website, please add an address in 'My Profile' to continue.");
            return "order_fail";
        }

        model.addAttribute("listAddresses", listAddresses);
        model.addAttribute("customer", customer);
        model.addAttribute("primaryAddressAsDefault", primaryAddressAsDefault);
        return "addresses";
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = Utilities.getEmailOfLoggedInCustomer(request);
        return customerService.getCustomerByEmail(email);
    }

    @GetMapping("/address_book/new")
    public String addNewAddress(Model model, HttpServletRequest request) {
        List<City> listCities = customerService.listAllCities();
        model.addAttribute("listCities", listCities);
        model.addAttribute("address", new Address());
        model.addAttribute("pageTitle", "Add New Address");

        return "forms/address_form";
    }

    @PostMapping("/address_book/save")
    public String saveNewAddress(Address address, HttpServletRequest request, RedirectAttributes rAttributes) {
        Customer customer = getAuthenticatedCustomer(request);
        address.setCustomer(customer);
        addressService.save(address);

        String redirectOption = request.getParameter("redirect");
        String redirectURL = "redirect:/address_book";

        if ("checkout".equals(redirectOption)) {
            redirectURL += "?redirect=checkout";
        }

        rAttributes.addFlashAttribute("message", "The address has been saved.");
        return redirectURL;
    }

    @GetMapping("/address_book/edit/{id}")
    public String editAddress(@PathVariable("id") Integer addressId, Model model, HttpServletRequest request) {
        Customer customer = getAuthenticatedCustomer(request);
        List<City> listCities = customerService.listAllCities();
        List<District> listDistricts = customerService.listAllDistrictsFromCustomerCity(customer);
        List<Ward> listWards = customerService.listAllWardsFromCustomerDistrict(customer);

        Address address = addressService.get(addressId, customer.getId());

        model.addAttribute("address", address);
        model.addAttribute("listCities", listCities);
        model.addAttribute("listDistricts", listDistricts);
        model.addAttribute("listWards", listWards);
        model.addAttribute("pageTitle", "Edit Address (ID: " + addressId + ")");
        return "forms/address_form";
    }

    @GetMapping("/address_book/delete/{id}")
    public String deleteAddress(@PathVariable("id") Integer addressId, RedirectAttributes rAttributes,
            HttpServletRequest request) {
        Customer customer = getAuthenticatedCustomer(request);
        addressService.delete(addressId, customer.getId());

        rAttributes.addFlashAttribute("message", "The address has been deleted");
        return "redirect:/address_book";
    }

    @GetMapping("/address_book/default/{id}")
    public String setDefaultAddress(@PathVariable("id") Integer addressId, RedirectAttributes rAttributes,
            HttpServletRequest request) {
        Customer customer = getAuthenticatedCustomer(request);
        addressService.setDefaultAddress(addressId, customer.getId());

        String redirectOption = request.getParameter("redirect");
        String redirectURL = "redirect:/address_book";

        if ("cart".equals(redirectOption)) {
            redirectURL = "redirect:/cart";
        } else if ("checkout".equals(redirectOption)) {
            redirectURL = "redirect:/checkout";
        }

        return redirectURL;
    }
}
