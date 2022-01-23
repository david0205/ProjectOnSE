package com.gearz;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.gearz.security.oauth.CustomerOAuth2User;
import com.gearz.setting.EmailSettingCollection;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public class Utilities {

    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    public static JavaMailSenderImpl prepareMailSender(EmailSettingCollection settings) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // SMTP server information
        mailSender.setHost(settings.getSmtpHost());
        mailSender.setPort(settings.getPort());
        mailSender.setUsername(settings.getUsername());
        mailSender.setPassword(settings.getPassword());

        // Setting SMTP server properties
        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.smtp.auth", settings.getSmtpAuth());
        mailProperties.setProperty("mail.smtp.starttls.enable", settings.getSmtpSecured());

        mailSender.setJavaMailProperties(mailProperties);

        return mailSender;
    }

    public static String getEmailOfLoggedInCustomer(HttpServletRequest request) {
        Object userPrincipal = request.getUserPrincipal();
        if (userPrincipal == null) {
            return null;
        }
        String customerEmail = null;

        if (userPrincipal instanceof UsernamePasswordAuthenticationToken
                || userPrincipal instanceof RememberMeAuthenticationToken) {
            customerEmail = request.getUserPrincipal().getName();
        } else if (userPrincipal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) userPrincipal;
            CustomerOAuth2User oauth2User = (CustomerOAuth2User) oauth2Token.getPrincipal();
            customerEmail = oauth2User.getEmail();
        }

        return customerEmail;
    }

    public static String currencyFormat(float amount) {
        String pattern = "$###,###.##";
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat(pattern, decimalFormatSymbols);
        return formatter.format(amount);
    }
}
