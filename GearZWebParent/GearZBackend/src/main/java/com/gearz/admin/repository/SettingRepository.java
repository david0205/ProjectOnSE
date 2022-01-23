package com.gearz.admin.repository;

import java.util.List;

import com.gearz.common.entity.Setting;
import com.gearz.common.entity.SettingCategory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, String> {
    List<Setting> findByCategory(SettingCategory category);
}
