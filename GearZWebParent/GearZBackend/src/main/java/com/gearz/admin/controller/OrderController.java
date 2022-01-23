package com.gearz.admin.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.gearz.admin.security.UserDetailsSecurity;
import com.gearz.admin.service.OrderService;
import com.gearz.admin.service.ProductService;
import com.gearz.common.entity.City;
import com.gearz.common.entity.District;
import com.gearz.common.entity.Order;
import com.gearz.common.entity.OrderDetail;
import com.gearz.common.entity.OrderStatus;
import com.gearz.common.entity.OrderTracking;
import com.gearz.common.entity.Product;
import com.gearz.common.entity.Ward;
import com.gearz.common.exception.OrderNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private ProductService productService;

    @GetMapping("/orders/details/{id}")
    public String viewOrderDetails(@PathVariable("id") Integer id, Model model, RedirectAttributes rAttributes,
            HttpServletRequest request, @AuthenticationPrincipal UserDetailsSecurity loggedUser) {
        try {
            Order order = service.get(id);

            boolean forAdminAndSalesperson = false;
            if (loggedUser.hasRole("Admin") || loggedUser.hasRole("Salesperson")) {
                forAdminAndSalesperson = true;
            }

            model.addAttribute("forAdminAndSalesperson", forAdminAndSalesperson);
            model.addAttribute("order", order);
            return "fake-modals/order_details_modal";
        } catch (OrderNotFoundException e) {
            rAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer id, Model model, RedirectAttributes rAttributes) {
        try {
            service.delete(id);
            rAttributes.addFlashAttribute("message", "The order ID " + id + " has been deleted");
        } catch (OrderNotFoundException e) {
            rAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/orders/edit/{id}")
    public String editOrder(@PathVariable("id") Integer id, HttpServletRequest request, Model model,
            RedirectAttributes rAttributes) {
        try {
            Order order = service.get(id);
            List<City> cities = service.listAllCities();
            List<District> districts = service.listAllDistrictsBasedOnCityInOrder(order);
            List<Ward> wards = service.listAllWardsBasedOnDistrictInOrder(order);

            model.addAttribute("pageTitle", "Edit order (ID: " + id + ")");
            model.addAttribute("order", order);
            model.addAttribute("cities", cities);
            model.addAttribute("districts", districts);
            model.addAttribute("wards", wards);

            return "forms/order";
        } catch (OrderNotFoundException e) {
            rAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/orders/save")
    public String saveOrder(Order order, HttpServletRequest request, RedirectAttributes rAttributes) {
        order.setCity(request.getParameter("cityName"));
        order.setDistrict(request.getParameter("districtName"));
        order.setWard(request.getParameter("wardName"));

        updateProductDetails(order, request);
        updateOrderTracking(order, request);

        service.save(order);
        rAttributes.addFlashAttribute("success", "The order ID " + order.getId() + " has been updated");
        return "redirect:/orders/edit/" + order.getId();
    }

    private void updateOrderTracking(Order order, HttpServletRequest request) {
        String[] trackingIds = request.getParameterValues("trackingId");
        String[] trackingDates = request.getParameterValues("trackingDate");
        String[] trackingDetails = request.getParameterValues("trackingDetail");
        String[] trackingStatuses = request.getParameterValues("trackingStatus");

        List<OrderTracking> orderTrackings = order.getOrderTrackings();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        for (int i = 0; i < trackingIds.length; i++) {
            OrderTracking trackingRecord = new OrderTracking();
            Integer trackingId = Integer.parseInt(trackingIds[i]);
            if (trackingId > 0) {
                trackingRecord.setId(trackingId);
            }

            trackingRecord.setOrder(order);
            trackingRecord.setStatus(OrderStatus.valueOf(trackingStatuses[i]));
            trackingRecord.setStatusDetail(trackingDetails[i]);
            try {
                trackingRecord.setUpdatedTime(formatter.parse(trackingDates[i]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            orderTrackings.add(trackingRecord);
        }
    }

    private void updateProductDetails(Order order, HttpServletRequest request) {
        String[] detailIds = request.getParameterValues("detailId");
        String[] productIds = request.getParameterValues("productId");
        String[] productCosts = request.getParameterValues("_productCost");
        String[] quantities = request.getParameterValues("_quantity");
        String[] productUnitPrices = request.getParameterValues("_productUnitPrice");
        String[] productSubtotals = request.getParameterValues("_productSubtotal");
        String[] productShippingCosts = request.getParameterValues("_productShippingCost");

        Set<OrderDetail> orderDetails = order.getOrderDetails();

        for (int i = 0; i < detailIds.length; i++) {
            OrderDetail orderDetail = new OrderDetail();
            Integer detailId = Integer.parseInt(detailIds[i]);
            if (detailId > 0) {
                orderDetail.setId(detailId);
            }
            orderDetail.setOrder(order);
            orderDetail.setProduct(new Product(Integer.parseInt(productIds[i])));
            orderDetail.setProductCost(Float.parseFloat(productCosts[i]));
            orderDetail.setSubtotal(Float.parseFloat(productSubtotals[i]));
            orderDetail.setShippingCost(Float.parseFloat(productShippingCosts[i]));
            orderDetail.setQuantity(Integer.parseInt(quantities[i]));
            orderDetail.setUnitPrice(Float.parseFloat(productUnitPrices[i]));

            orderDetails.add(orderDetail);
        }
    }

    @GetMapping("/orders/search_product")
    public String showProductPageModal(Model model) {
        List<Product> products = productService.listAllProducts();
        model.addAttribute("listProducts", products);
        return "fake-modals/search_product_modal";
    }
}
