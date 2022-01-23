package com.gearz.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.gearz.Utilities;
import com.gearz.common.entity.City;
import com.gearz.common.entity.Customer;
import com.gearz.common.entity.District;
import com.gearz.common.entity.Ward;
import com.gearz.common.exception.CustomerNotFoundException;
import com.gearz.security.CustomerUserDetails;
import com.gearz.security.oauth.CustomerOAuth2User;
import com.gearz.service.CustomerService;
import com.gearz.setting.EmailSettingCollection;
import com.gearz.setting.SettingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SettingService settingService;

    @GetMapping("/account")
    public String registerNewCustomerPage(Model model) {
        model.addAttribute("pageTitle", "Sign in or Create an Account");
        model.addAttribute("customer", new Customer());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "account";
        }

        return "redirect:/";
    }

    @PostMapping("/create_account")
    public String createAccount(Customer customer, RedirectAttributes rAttributes, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        customerService.registerCustomer(customer);
        sendVerificationEmail(request, customer);

        rAttributes.addFlashAttribute("success",
                "You have registered successfully as a customer.\n Check your email to verify the account.");

        return "redirect:/account";
    }

    private void sendVerificationEmail(HttpServletRequest request, Customer customer)
            throws MessagingException, UnsupportedEncodingException {
        EmailSettingCollection emailSettings = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = Utilities.prepareMailSender(emailSettings);
        String verifyURL = Utilities.getSiteURL(request) + "/verify?code=" + customer.getVerificationCode();

        // Outgoing message information
        String toEmailAddress = customer.getEmail();
        String emailSubject = emailSettings.getCustomerVerifySubject();
        String emailContent = emailSettings.getCustomerVerifyContent();

        // Replace [[URL]] with verify url, [[name]] with customer's full name
        emailContent = emailContent.replace("[[URL]]", verifyURL);
        emailContent = emailContent.replace("[[name]]", customer.getFullName());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(emailSettings.getSenderEmail(), emailSettings.getSenderName());
        helper.setTo(toEmailAddress);
        helper.setSubject(emailSubject);
        helper.setText(emailContent, true); // indicates that email format will be in HTML

        mailSender.send(message);
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code, Model model) {
        boolean verified = customerService.verify(code);
        return "verify/" + (verified ? "success" : "fail");
    }

    @GetMapping("/profile")
    public String accountDetails(Model model, HttpServletRequest request) {
        String emailOfLoggedInCustomer = Utilities.getEmailOfLoggedInCustomer(request);
        Customer customer = customerService.getCustomerByEmail(emailOfLoggedInCustomer);
        List<City> cities = customerService.listAllCities();
        List<District> districts = customerService.listAllDistrictsFromCustomerCity(customer);
        List<Ward> wards = customerService.listAllWardsFromCustomerDistrict(customer);

        model.addAttribute("pageTitle", "Personal Details");
        model.addAttribute("customer", customer);
        model.addAttribute("cities", cities);
        model.addAttribute("districts", districts);
        model.addAttribute("wards", wards);
        return "profile";
    }

    @PostMapping("/update_profile")
    public String updateCustomerProfile(Customer customer, Model model, RedirectAttributes rAttributes,
            HttpServletRequest request) {
        customerService.update(customer);
        rAttributes.addFlashAttribute("success", "Profile successfully updated");
        rAttributes.addFlashAttribute("message", "Primary address successfully updated");

        updateLoggedInCustomerName(customer, request);

        String redirectOption = request.getParameter("redirect");
        String redirectURL = "redirect:/profile";

        if ("address_book".equals(redirectOption)) {
            redirectURL = "redirect:/address_book";
        } else if ("cart".equals(redirectOption)) {
            redirectURL = "redirect:/cart";
        } else if ("checkout".equals(redirectOption)) {
            redirectURL = "redirect:/address_book?redirect=checkout";
        }

        return redirectURL;
    }

    private void updateLoggedInCustomerName(Customer customer, HttpServletRequest request) {
        Object userPrincipal = request.getUserPrincipal();

        if (userPrincipal instanceof UsernamePasswordAuthenticationToken
                || userPrincipal instanceof RememberMeAuthenticationToken) {
            CustomerUserDetails userDetails = getCustomerUserDetailsObject(userPrincipal);
            Customer loggedInCustomer = userDetails.getCustomer();
            loggedInCustomer.setFullName(customer.getFullName());
        } else if (userPrincipal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) userPrincipal;
            CustomerOAuth2User oauth2User = (CustomerOAuth2User) oauth2Token.getPrincipal();
            oauth2User.setFullName(customer.getFullName());
        }
    }

    private CustomerUserDetails getCustomerUserDetailsObject(Object principal) {
        CustomerUserDetails userDetails = null;

        if (principal instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
            userDetails = (CustomerUserDetails) token.getPrincipal();
        } else if (principal instanceof RememberMeAuthenticationToken) {
            RememberMeAuthenticationToken token = (RememberMeAuthenticationToken) principal;
            userDetails = (CustomerUserDetails) token.getPrincipal();
        }

        return userDetails;
    }

    @PostMapping("/forgot_password")
    public String proceedRequestForm(HttpServletRequest request, RedirectAttributes rAttributes) {
        String email = request.getParameter("email_forgot");
        try {
            String token = customerService.updateResetPasswordToken(email);
            String link = Utilities.getSiteURL(request) + "/reset_password?token=" + token;
            sendResetPasswordEmail(link, email);
            rAttributes.addFlashAttribute("success", "A reset password link has been sent. Please check your email.");
        } catch (CustomerNotFoundException e) {
            rAttributes.addFlashAttribute("failed", e.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            rAttributes.addFlashAttribute("failed", "Could not send the email.");
        }

        return "redirect:/account";
    }

    private void sendResetPasswordEmail(String link, String email)
            throws UnsupportedEncodingException, MessagingException {
        EmailSettingCollection emailSettings = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = Utilities.prepareMailSender(emailSettings);

        String toEmailAddress = email;
        String emailSubject = "Reset your password";
        String emailContent = "<p>Hello!</p>" + "<p>Seems like you forgot the password for your GearZ account.</p>"
                + "If this is true, click below to reset your password." + "<p><a href=\"" + link
                + "\">Reset My Password</a></p><br>"
                + "<p>If you did not forgot your password, you can safely ignore this email.</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(emailSettings.getSenderEmail(), emailSettings.getSenderName());
        helper.setTo(toEmailAddress);
        helper.setSubject(emailSubject);
        helper.setText(emailContent, true);
        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param("token") String token, Model model) {
        Customer customer = customerService.getByResetPasswordToken(token);
        if (customer != null) {
            model.addAttribute("token", token);
        } else {
            return "reset_password/fail";
        }
        return "reset_password/form";
    }

    @PostMapping("/reset_password")
    public String proceedResetPasswordForm(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("new_password");

        try {
            customerService.updatePassword(token, newPassword);
            return "reset_password/success";
        } catch (CustomerNotFoundException e) {
            model.addAttribute("message", e.getMessage());
            return "reset_password/fail";
        }
    }

}
