package com.gearz.setting;

import java.util.List;

import com.gearz.common.entity.Setting;
import com.gearz.common.entity.SettingCollection;

public class EmailSettingCollection extends SettingCollection {

    public EmailSettingCollection(List<Setting> lSettings) {
        super(lSettings);
    }

    public String getSmtpHost() {
        return super.getValue("MAIL_HOST");
    }

    public Integer getPort() {
        return Integer.parseInt(super.getValue("MAIL_PORT"));
    }

    public String getUsername() {
        return super.getValue("MAIL_USERNAME");
    }

    public String getPassword() {
        return super.getValue("MAIL_PASSWORD");
    }

    public String getSmtpAuth() {
        return super.getValue("SMTP_AUTH");
    }

    public String getSmtpSecured() {
        return super.getValue("SMTP_SECURED");
    }

    public String getSenderEmail() {
        return super.getValue("MAIL_FROM");
    }

    public String getSenderName() {
        return super.getValue("MAIL_SENDER_NAME");
    }

    public String getCustomerVerifySubject() {
        return super.getValue("CUSTOMER_VERIFY_SUBJECT");
    }

    public String getCustomerVerifyContent() {
        return super.getValue("CUSTOMER_VERIFY_CONTENT");
    }

    public String getOrderConfirmationSubject() {
        return super.getValue("ORDER_CONFIRMATION_SUBJECT");
    }

    public String getOrderConfirmationContent() {
        return super.getValue("ORDER_CONFIRMATION_CONTENT");
    }

}
