package com.gearz.admin.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import com.gearz.admin.repository.CustomerRepository;
import com.gearz.common.entity.Customer;
import com.gearz.common.exception.CustomerNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }

    public void changeCustomerEnabledStatus(Integer id, boolean enabled) {
        customerRepository.updateEnabledStatus(id, enabled);
    }

    public Customer get(Integer id) throws CustomerNotFoundException {
        try {
            return customerRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new CustomerNotFoundException("Could not find any customer with ID " + id);
        }
    }

    public void delete(Integer id) throws CustomerNotFoundException {
        Long count = customerRepository.countById(id);
        if (count == null || count == 0) {
            throw new CustomerNotFoundException("No user ID " + id + " was found");
        }
        customerRepository.deleteById(id);
    }
}
