package com.gearz.service;

import java.util.List;

import javax.transaction.Transactional;

import com.gearz.common.entity.Address;
import com.gearz.common.entity.Customer;
import com.gearz.repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> listAddresses(Customer customer) {
        return addressRepository.findByCustomer(customer);
    }

    public void save(Address address) {
        addressRepository.save(address);
    }

    public Address get(Integer addressId, Integer customerId) {
        return addressRepository.findByIdAndCustomer(addressId, customerId);
    }

    public void delete(Integer addressId, Integer customerId) {
        addressRepository.deleteByIdAndCustomer(addressId, customerId);
    }

    public void setDefaultAddress(Integer defaultAddressId, Integer customerId) {
        if (defaultAddressId > 0) {
            addressRepository.setDefaultAddress(defaultAddressId);
        }
        addressRepository.setNonDefaultForOtherAddresses(defaultAddressId, customerId);
    }

    public Address getDefaultAddress(Customer customer) {
        return addressRepository.findDefaultByCustomer(customer.getId());
    }
}
