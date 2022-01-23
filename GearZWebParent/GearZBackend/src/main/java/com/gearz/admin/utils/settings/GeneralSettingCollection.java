package com.gearz.admin.utils.settings;

import java.util.List;

import com.gearz.common.entity.Setting;
import com.gearz.common.entity.SettingCollection;

public class GeneralSettingCollection extends SettingCollection {

    public GeneralSettingCollection(List<Setting> lSettings) {
        super(lSettings);
    }
    
    public void updateSiteLogo(String value) {
        super.update("SITE_LOGO", value);
    }
}
