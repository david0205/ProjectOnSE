package com.gearz.admin.service;

import java.util.ArrayList;
import java.util.List;

import com.gearz.admin.repository.SettingRepository;
import com.gearz.admin.utils.settings.GeneralSettingCollection;
import com.gearz.common.entity.Setting;
import com.gearz.common.entity.SettingCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    @Autowired
    private SettingRepository repo;

    public List<Setting> listAllSettings() {
        return (List<Setting>) repo.findAll();
    }

    public GeneralSettingCollection getGeneralSettings() {
        List<Setting> settings = new ArrayList<>();
        List<Setting> general_settings = repo.findByCategory(SettingCategory.GENERAL);

        settings.addAll(general_settings);
        return new GeneralSettingCollection(settings);
    }

    public void saveAll(Iterable<Setting> settings) {
        repo.saveAll(settings);
    }

    public List<Setting> getMailServerSettings() {
        return repo.findByCategory(SettingCategory.MAIL_SERVER);
    }

    public List<Setting> getMailTemplateSettings() {
        return repo.findByCategory(SettingCategory.MAIL_TEMPLATE);
    }

    public List<Setting> getPaymentSettings() {
        return repo.findByCategory(SettingCategory.PAYMENT);
    }
}
