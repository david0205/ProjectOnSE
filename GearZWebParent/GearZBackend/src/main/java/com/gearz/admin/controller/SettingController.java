package com.gearz.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gearz.admin.service.SettingService;
import com.gearz.admin.utils.FileUploadUtil;
import com.gearz.admin.utils.settings.GeneralSettingCollection;
import com.gearz.common.entity.Setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SettingController {

    @Autowired
    private SettingService service;

    @PostMapping("/settings/save_payment")
    public String savePaymentSettings(HttpServletRequest request, RedirectAttributes rAttributes) {
        List<Setting> paymentSettings = service.getPaymentSettings();
        updateSettingValues(request, paymentSettings);

        rAttributes.addFlashAttribute("message", "Payment settings have been saved.");
        return "redirect:/";
    }

    @PostMapping("/settings/save_mail_server")
    public String saveMailServerSettings(HttpServletRequest request, RedirectAttributes rAttributes) {
        List<Setting> mailServerSettings = service.getMailServerSettings();
        updateSettingValues(request, mailServerSettings);

        rAttributes.addFlashAttribute("message", "Mail server settings have been saved.");
        return "redirect:/";
    }

    @PostMapping("/settings/save_mail_templates")
    public String saveMailTemplateSettings(HttpServletRequest request, RedirectAttributes rAttributes) {
        List<Setting> mailTemplateSettings = service.getMailTemplateSettings();
        updateSettingValues(request, mailTemplateSettings);

        rAttributes.addFlashAttribute("message", "Mail template settings have been saved.");
        return "redirect:/";
    }

    @PostMapping("/settings/save_general")
    public String saveGeneralSettings(@RequestParam("imageFile") MultipartFile multipartFile,
            HttpServletRequest request, RedirectAttributes rAttributes) throws IOException {
        GeneralSettingCollection settingCollection = service.getGeneralSettings();

        saveSiteLogo(multipartFile, settingCollection);
        updateSettingValues(request, settingCollection.list());

        rAttributes.addFlashAttribute("message", "General settings have been saved.");
        return "redirect:/";
    }

    private void saveSiteLogo(MultipartFile multipartFile, GeneralSettingCollection settingCollection)
            throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String value = "/site-logo/" + fileName;
            settingCollection.updateSiteLogo(value);
            String uploadDir = "../site-logo/";
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
    }

    private void updateSettingValues(HttpServletRequest request, List<Setting> lSettings) {
        for (Setting setting : lSettings) {
            String value = request.getParameter(setting.getKey());
            if (value != null) {
                setting.setValue(value);
            }
        }
        service.saveAll(lSettings);
    }
}
