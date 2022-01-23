package com.gearz.admin.controller.rest;

import java.util.List;

import com.gearz.admin.repository.CityRepository;
import com.gearz.common.entity.City;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityRestController {
    
    @Autowired
    private CityRepository repo;

    @GetMapping("/cities/list")
    public List<City> lCities() {
        return repo.findAllByOrderByNameAsc();
    }

    @PostMapping("/cities/save")
    public String save(@RequestBody City city) {
        City savedCity = repo.save(city);
        return String.valueOf(savedCity.getId());
    }

    @DeleteMapping("/cities/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        repo.deleteById(id);
    }
}
