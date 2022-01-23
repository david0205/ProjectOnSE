package com.gearz.admin.dto;

import com.gearz.common.entity.City;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDTO {
    private Integer id;
    private String name;
    private City city;
}
