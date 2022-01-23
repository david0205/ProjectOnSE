package com.gearz.admin.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import com.gearz.admin.exceptions.ShippingRateAlreadyExistsException;
import com.gearz.admin.exceptions.ShippingRateNotFoundException;
import com.gearz.admin.repository.CityRepository;
import com.gearz.admin.repository.DistrictRepository;
import com.gearz.admin.repository.ProductRepository;
import com.gearz.admin.repository.ShippingRateRepository;
import com.gearz.common.entity.City;
import com.gearz.common.entity.District;
import com.gearz.common.entity.Product;
import com.gearz.common.entity.ShippingRate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ShippingRateService {

    private static final int DIM_DIVISOR = 139;

    @Autowired
    private ShippingRateRepository shippingRateRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistrictRepository districtRepository;

    public List<ShippingRate> listAllShippingRates() {
        return shippingRateRepository.findAll();
    }

    public List<City> listAllCities() {
        return cityRepository.findAllByOrderByNameAsc();
    }

    public List<District> listAllDistrictsFromCity(ShippingRate shippingRate) {
        return districtRepository.findByCityOrderByNameAsc(shippingRate.getCity());
    }

    public void save(ShippingRate shippingRateInForm) throws ShippingRateAlreadyExistsException {
        ShippingRate shippingRateInDB = shippingRateRepository
                .findByCityAndDistrict(shippingRateInForm.getCity().getId(), shippingRateInForm.getDistrict());
        boolean foundExistingRateInNewMode = shippingRateInForm.getId() == null && shippingRateInDB != null;
        boolean foundDifferentExistingRateInEditMode = shippingRateInForm.getId() != null && shippingRateInDB != null;

        if (foundDifferentExistingRateInEditMode || foundExistingRateInNewMode) {
            throw new ShippingRateAlreadyExistsException("Already existed a rate for the destination "
                    + shippingRateInForm.getCity().getName() + ", " + shippingRateInForm.getDistrict());
        }
        shippingRateRepository.save(shippingRateInForm);
    }

    public ShippingRate get(Integer id) throws ShippingRateNotFoundException {
        try {
            return shippingRateRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ShippingRateNotFoundException("Could not find shipping rate with ID " + id);
        }
    }

    public void updateCODSupport(Integer id, boolean codSupported) throws ShippingRateNotFoundException {
        Long count = shippingRateRepository.countById(id);
        if (count == null || count == 0) {
            throw new ShippingRateNotFoundException("Could not find shipping rate with ID " + id);
        }
        shippingRateRepository.updateCODSupport(id, codSupported);
    }

    public void delete(Integer id) throws ShippingRateNotFoundException {
        Long count = shippingRateRepository.countById(id);
        if (count == null || count == 0) {
            throw new ShippingRateNotFoundException("Could not find shipping rate with ID " + id);
        }
        shippingRateRepository.deleteById(id);
    }

    public float calculateShippingCost(Integer productId, Integer cityId, String district)
            throws ShippingRateNotFoundException {
        ShippingRate shippingRate = shippingRateRepository.findByCityAndDistrict(cityId, district);

        if (shippingRate == null) {
            throw new ShippingRateNotFoundException("No shipping rate found");
        }

        Product product = productRepository.findById(productId).get();
        float dimWeight = (product.getLength() * product.getWidth() * product.getHeight()) / DIM_DIVISOR;
        float finalWeight = product.getWeight() > dimWeight ? product.getWeight() : dimWeight;

        return finalWeight * shippingRate.getRate();
    }
}
