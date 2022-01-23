package com.gearz.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.gearz.admin.repository.SettingRepository;
import com.gearz.common.entity.Setting;
import com.gearz.common.entity.SettingCategory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SettingTest {
    @Autowired
    SettingRepository repo;

    @Test
    public void testCreateGeneralSettings() {
        // Setting siteName = Setting.builder().key("SITE_NAME").value("GearZ").category(SettingCategory.GENERAL).build();
        // Setting siteLogo = Setting.builder().key("SITE_LOGO").value("GeaZLogo.png").category(SettingCategory.GENERAL).build();
        Setting copyright = Setting.builder().key("COPYRIGHT").value("Copyright (c) 2021 - GearZ, DLT Ltd.").category(SettingCategory.GENERAL).build();
        Setting savedSetting = repo.save(copyright);

        assertThat(savedSetting).isNotNull();
    }
}
