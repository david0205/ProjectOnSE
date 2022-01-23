package com.gearz.controller.rest;

import java.util.ArrayList;
import java.util.List;

import com.gearz.common.entity.City;
import com.gearz.common.entity.District;
import com.gearz.common.entity.Ward;
import com.gearz.dto.DistrictDTO;
import com.gearz.dto.WardDTO;
import com.gearz.repository.CityRepository;
import com.gearz.repository.DistrictRepository;
import com.gearz.repository.WardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterRestController {

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/cities/list")
    public List<City> lCities() {
        return cityRepository.findAllByOrderByNameAsc();
    }

    @GetMapping("/districts/list_by_city/{id}")
    public List<DistrictDTO> listByCity(@PathVariable("id") Integer cityId) {
        List<District> lDistricts = districtRepository.findByCityOrderByIdAsc(cityRepository.findById(cityId).get());
        List<DistrictDTO> result = new ArrayList<>();

        for (District district : lDistricts) {
            result.add(new DistrictDTO(district.getId(), district.getName()));
        }

        return result;
    }

    @GetMapping("/wards/list_by_district/{id}")
    public List<WardDTO> listByDistrict(@PathVariable("id") Integer districtId) {
        List<WardDTO> result = new ArrayList<>();
        List<Ward> lWards = wardRepository.findByDistrictOrderByNameAsc(districtRepository.findById(districtId).get());

        for (Ward ward : lWards) {
            result.add(new WardDTO(ward.getId(), ward.getName()));
        }

        return result;
    }
}
