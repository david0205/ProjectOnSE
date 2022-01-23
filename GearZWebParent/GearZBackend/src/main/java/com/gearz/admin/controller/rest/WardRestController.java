package com.gearz.admin.controller.rest;

import java.util.ArrayList;
import java.util.List;

import com.gearz.admin.dto.WardDTO;
import com.gearz.admin.repository.DistrictRepository;
import com.gearz.admin.repository.WardRepository;
import com.gearz.common.entity.Ward;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WardRestController {

    @Autowired
    private WardRepository repo;

    @Autowired
    private DistrictRepository districtRepository;

    @GetMapping("/wards/list_by_district/{id}")
    public List<WardDTO> listByDistrict(@PathVariable("id") Integer districtId) {
        List<WardDTO> result = new ArrayList<>();
        List<Ward> lWards = repo.findByDistrictOrderByNameAsc(districtRepository.findById(districtId).get());

        for (Ward ward : lWards) {
            result.add(new WardDTO(ward.getId(), ward.getName(), ward.getDistrict()));
        }

        return result;
    }

    @PostMapping("/wards/save")
    public String save(@RequestBody Ward ward) {
        Ward savedWard = repo.save(ward);
        return String.valueOf(savedWard);
    }

    @DeleteMapping("/wards/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        repo.deleteById(id);
    }
}
