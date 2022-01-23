package com.gearz.common.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SettingCollection {
    private List<Setting> lSettings;

    // Returns a setting object by key
    public Setting get(String key) {
        int index = lSettings.indexOf(Setting.builder().key(key).build());
        if (index >= 0) {
            return lSettings.get(index);
        }
        return null;
    }

    // Returs the value based on the given key
    public String getValue(String key) {
        Setting setting = get(key);
        if (setting != null) {
            return setting.getValue();
        }
        return null;
    }

    // Update a setting based on the given key and value
    public void update(String key, String value) {
        Setting setting = get(key);
        if (setting != null && value != null) {
            setting.setValue(value);
        }
    }

    public List<Setting> list() {
        return lSettings;
    }
}
