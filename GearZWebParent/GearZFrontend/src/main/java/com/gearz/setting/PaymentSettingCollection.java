package com.gearz.setting;

import java.util.List;

import com.gearz.common.entity.Setting;
import com.gearz.common.entity.SettingCollection;

public class PaymentSettingCollection extends SettingCollection {

    public PaymentSettingCollection(List<Setting> lSettings) {
        super(lSettings);
    }

    public String getURL() {
        return super.getValue("PAYPAL_API_BASE_URL");
    }

    public String getClientID() {
        return super.getValue("PAYPAL_API_CLIENT_ID");
    }

    public String getClientSecret() {
        return super.getValue("PAYPAL_API_CLIENT_SECRET");
    }
}
