package com.gearz.controller;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.gearz.Utilities;
import com.gearz.checkout.paypal.PaypalApiException;
import com.gearz.checkout.paypal.PaypalService;
import com.gearz.common.entity.Address;
import com.gearz.common.entity.CartItem;
import com.gearz.common.entity.Customer;
import com.gearz.common.entity.Order;
import com.gearz.common.entity.PaymentMethod;
import com.gearz.common.entity.ShippingRate;
import com.gearz.dto.CheckoutInfomation;
import com.gearz.service.AddressService;
import com.gearz.service.CartService;
import com.gearz.service.CheckoutService;
import com.gearz.service.CustomerService;
import com.gearz.service.OrderService;
import com.gearz.service.ShippingRateService;
import com.gearz.setting.EmailSettingCollection;
import com.gearz.setting.PaymentSettingCollection;
import com.gearz.setting.SettingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ShippingRateService shippingRateService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private PaypalService paypalService;

    @GetMapping("/checkout")
    public String viewCheckoutPage(HttpServletRequest request, Model model) {
        Customer authenticatedCustomer = getAuthenticatedCustomer(request);

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

        if (defaultAddress != null) {
            model.addAttribute("shippingAddress", defaultAddress.toString());
            shippingRate = shippingRateService.getShippingRateForAddress(defaultAddress);
        } else {
            model.addAttribute("shippingAddress", authenticatedCustomer.getFullAddress());
            shippingRate = shippingRateService.getShippingRateForCustomer(authenticatedCustomer);
        }

        if (shippingRate == null) {
            return "redirect:/cart";
        }

        List<CartItem> cartItems = cartService.listCartItems(authenticatedCustomer);
        CheckoutInfomation checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate);

        PaymentSettingCollection paymentSettings = settingService.getPaymentSettings();
        String paypal_client_id = paymentSettings.getClientID();

        model.addAttribute("paypal_client_id", paypal_client_id);
        model.addAttribute("customer", authenticatedCustomer);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("checkoutInfo", checkoutInfo);
        return "checkout";
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = Utilities.getEmailOfLoggedInCustomer(request);
        return customerService.getCustomerByEmail(email);
    }

    @PostMapping("/place_order")
    public String placeOrder(HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        String paymentType = request.getParameter("payment");
        System.out.println(paymentType);
        PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentType);

        Customer authenticatedCustomer = getAuthenticatedCustomer(request);

        Address defaultAddress = addressService.getDefaultAddress(authenticatedCustomer);
        ShippingRate shippingRate = null;

        if (defaultAddress != null) {
            shippingRate = shippingRateService.getShippingRateForAddress(defaultAddress);
        } else {
            shippingRate = shippingRateService.getShippingRateForCustomer(authenticatedCustomer);
        }

        List<CartItem> cartItems = cartService.listCartItems(authenticatedCustomer);
        CheckoutInfomation checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate);

        Order order = orderService.createOrder(authenticatedCustomer, defaultAddress, cartItems, paymentMethod,
                checkoutInfo);
        cartService.deleteByCustomer(authenticatedCustomer);

        sendOrderConfirmationEmail(request, order);

        return "complete_order";
    }

    private void sendOrderConfirmationEmail(HttpServletRequest request, Order order)
            throws UnsupportedEncodingException, MessagingException {
        EmailSettingCollection emailSettings = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = Utilities.prepareMailSender(emailSettings);

        mailSender.setDefaultEncoding("utf-8");

        String toEmailAddress = order.getCustomer().getEmail();
        String emailSubject = emailSettings.getOrderConfirmationSubject();
        String emailContent = emailSettings.getOrderConfirmationContent();

        emailSubject = emailSubject.replace("[[orderId]]", String.valueOf(order.getId()));

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(emailSettings.getSenderEmail(), emailSettings.getSenderName());
        helper.setTo(toEmailAddress);
        helper.setSubject(emailSubject);

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss E, dd MMM yyyy");
        String orderTime = dateFormat.format(order.getOrderTime());

        String total = Utilities.currencyFormat(order.getTotal());

        emailContent = emailContent.replace("[[orderId]]", String.valueOf(order.getId()));
        emailContent = emailContent.replace("[[name]]", order.getCustomer().getFullName());
        emailContent = emailContent.replace("[[orderTime]]", orderTime);
        emailContent = emailContent.replace("[[shippingAddress]]", order.getShippingAddress());
        emailContent = emailContent.replace("[[total]]", total);
        emailContent = emailContent.replace("[[paymentMethod]]", order.getPaymentMethod().toString());

        helper.setText(emailContent, true);
        mailSender.send(message);
    }

    @PostMapping("/process_paypal_order")
    public String processPaypalOrder(HttpServletRequest request, Model model)
            throws UnsupportedEncodingException, MessagingException {
        String orderId = request.getParameter("orderId");
        String pageTitle = "";
        String message = "";
        try {
            if (paypalService.validateOrder(orderId)) {
                return placeOrder(request);
            } else {
                pageTitle = "Oops!";
                message = "Order information is invalid! Transaction could not be completed!";
            }
        } catch (PaypalApiException e) {
            message = "Error: " + e.getMessage();
        }
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("message", message);
        return "order_fail";
    }
}
