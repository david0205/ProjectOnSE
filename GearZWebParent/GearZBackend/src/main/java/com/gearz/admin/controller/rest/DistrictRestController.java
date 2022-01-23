package com.gearz.admin.controller.rest;

import java.util.ArrayList;
import java.util.List;

import com.gearz.admin.dto.DistrictDTO;
import com.gearz.admin.repository.CityRepository;
import com.gearz.admin.repository.DistrictRepository;
import com.gearz.common.entity.District;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistrictRestController {

    @Autowired
    private DistrictRepository repo;

    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/districts/list")
    public List<District> listAllDistricts() {
        return repo.findAllByOrderByNameAsc();
    }

    @GetMapping("/districts/list_by_city/{id}")
    public List<DistrictDTO> listByCity(@PathVariable("id") Integer cityId) {
        List<District> lDistricts = repo.findByCityOrderByNameAsc(cityRepository.findById(cityId).get());
        List<DistrictDTO> result = new ArrayList<>();

        for (District district : lDistricts) {
            result.add(new DistrictDTO(district.getId(), district.getName(), district.getCity()));
        }

        return result;
    }

    @PostMapping("/districts/save")
    public String save(@RequestBody District district) {
        District savedDistrict = repo.save(district);
        return String.valueOf(savedDistrict.getId());
    }

    @DeleteMapping("/districts/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        repo.deleteById(id);
    }
}
