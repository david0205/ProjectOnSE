package com.gearz.setting;

import java.util.List;

import com.gearz.common.entity.Setting;
import com.gearz.common.entity.SettingCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    @Autowired
    private SettingRepository repo;

    public List<Setting> getGeneralSettings() {
        return repo.findByCategory(SettingCategory.GENERAL);
    }

    public EmailSettingCollection getEmailSettings() {
        List<Setting> settings = repo.findByCategory(SettingCategory.MAIL_SERVER);
        settings.addAll(repo.findByCategory(SettingCategory.MAIL_TEMPLATE));

        return new EmailSettingCollection(settings);
    }

    public PaymentSettingCollection getPaymentSettings() {
        List<Setting> settings = repo.findByCategory(SettingCategory.PAYMENT);
        return new PaymentSettingCollection(settings);
    }
}
