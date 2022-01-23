package com.gearz;

import java.util.List;

import com.gearz.common.entity.City;
import com.gearz.common.entity.District;
import com.gearz.repository.CityRepository;
import com.gearz.repository.DistrictRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CityTest {

    @Autowired
    private CityRepository cRepository;

    @Autowired
    private DistrictRepository repo;

    @Test
    public void test1() {
        City city = cRepository.findById(2).get();
        List<District> districtsByCity = repo.findByCityOrderByIdAsc(city);
        for (District district : districtsByCity) {
            System.out.println(district.getName());
        }
    }
}
