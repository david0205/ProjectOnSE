package com.gearz.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gearz.admin.exceptions.ShippingRateAlreadyExistsException;
import com.gearz.admin.exceptions.ShippingRateNotFoundException;
import com.gearz.admin.service.ShippingRateService;
import com.gearz.common.entity.City;
import com.gearz.common.entity.District;
import com.gearz.common.entity.ShippingRate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ShippingRateController {

    @Autowired
    private ShippingRateService service;

    private String link = "";

    @GetMapping("/shipping_rates/new")
    public String addNewShippingRate(HttpServletRequest httpServletRequest, Model model) {
        link = httpServletRequest.getRequestURI();
        List<City> cities = service.listAllCities();

        model.addAttribute("shippingRate", new ShippingRate());
        model.addAttribute("listCities", cities);
        model.addAttribute("pageTitle", "Add new shipping rate");

        return "forms/shipping_rate";
    }

    @GetMapping("/shipping_rates/edit/{id}")
    public String editShippingRate(@PathVariable("id") Integer id, Model model, RedirectAttributes rAttributes) {
        try {
            ShippingRate rate = service.get(id);
            List<City> cities = service.listAllCities();
            List<District> districts = service.listAllDistrictsFromCity(rate);

            model.addAttribute("listCities", cities);
            model.addAttribute("listDistricts", districts);
            model.addAttribute("shippingRate", rate);
            model.addAttribute("pageTitle", "Edit shipping rate (ID: " + id + ")");

            return "forms/shipping_rate";
        } catch (ShippingRateNotFoundException e) {
            rAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/shipping_rates/save")
    public String saveShippingRate(ShippingRate rate, RedirectAttributes rAttributes) {
        try {
            service.save(rate);
            rAttributes.addFlashAttribute("success", "Shipping rate saved successfully.");
        } catch (ShippingRateAlreadyExistsException e) {
            rAttributes.addFlashAttribute("success", e.getMessage());
        }
        if (link.contains("/edit/")) {
            return "redirect:/shipping_rates/edit/" + rate.getId();
        }
        return "redirect:/shipping_rates/new";
    }

    @GetMapping("/shipping_rates/cod/{id}/enabled/{supported}")
    public String updateCODSupport(@PathVariable("id") Integer id, @PathVariable("supported") Boolean supported,
            Model model, RedirectAttributes rAttributes) {
        try {
            service.updateCODSupport(id, supported);
            rAttributes.addFlashAttribute("message", "COD support for shipping rate ID " + id + " has been updated");
        } catch (ShippingRateNotFoundException e) {
            rAttributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/shipping_rates/delete/{id}")
    public String deleteShippingRate(@PathVariable("id") Integer id, Model model, RedirectAttributes rAttributes) {
        try {
            service.delete(id);
            rAttributes.addFlashAttribute("message", "Shipping rate ID " + id + " has been deleted");
        } catch (Exception e) {
            rAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }

}
