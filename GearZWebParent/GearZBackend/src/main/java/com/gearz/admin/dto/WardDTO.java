package com.gearz.admin.dto;

import com.gearz.common.entity.District;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WardDTO {
    private Integer id;
    private String name;
    private District district;
}
