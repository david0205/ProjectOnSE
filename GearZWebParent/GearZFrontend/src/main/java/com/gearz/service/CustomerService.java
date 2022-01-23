package com.gearz.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.gearz.common.entity.AuthenticationType;
import com.gearz.common.entity.City;
import com.gearz.common.entity.Customer;
import com.gearz.common.entity.District;
import com.gearz.common.entity.Ward;
import com.gearz.common.exception.CustomerNotFoundException;
import com.gearz.repository.CityRepository;
import com.gearz.repository.CustomerRepository;
import com.gearz.repository.DistrictRepository;
import com.gearz.repository.WardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<City> listAllCities() {
        return cityRepository.findAll();
    }

    public List<District> listAllDistrictsFromCustomerCity(Customer customer) {
        return districtRepository.findByCityOrderByIdAsc(customer.getCity());
    }

    public List<Ward> listAllWardsFromCustomerDistrict(Customer customer) {
        return wardRepository.findByDistrictOrderByNameAsc(customer.getDistrict());
    }

    public boolean isEmailExisted(String email) {
        Customer customer = customerRepository.findByEmail(email);
        return customer == null;
    }

    public void registerCustomer(Customer customer) {
        encodePassword(customer);
        customer.setEnabled(false);
        customer.setCreatedTime(new Date());
        customer.setAuthenticationType(AuthenticationType.DATABASE);

        String randomCode = RandomString.make(64);
        customer.setVerificationCode(randomCode);

        customerRepository.save(customer);
    }

    private void encodePassword(Customer customer) {
        String encodedPassword = passwordEncoder.encode(customer.getFullPassword());
        customer.setFullPassword(encodedPassword);
    }

    public boolean verify(String verificationCode) {
        Customer customer = customerRepository.findByVerificationCode(verificationCode);

        if (customer == null || customer.isEnabled()) {
            return false;
        } else {
            customerRepository.enable(customer.getId());
            return true;
        }
    }

    public void updateAuthenticationType(Customer customer, AuthenticationType authType) {
        if (!customer.getAuthenticationType().equals(authType)) {
            customerRepository.updateAuthenticationType(customer.getId(), authType);
        }
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public void addNewOAuthLoginCustomer(String name, String email, AuthenticationType authenticationType) {
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFullPassword("");
        customer.setFullName(name);
        customer.setEnabled(true);
        customer.setCreatedTime(new Date());
        customer.setAuthenticationType(authenticationType);

        customerRepository.save(customer);
    }

    public void update(Customer customerNewDetails) {
        Customer customerOldDetails = customerRepository.findById(customerNewDetails.getId()).get();

        if (customerOldDetails.getAuthenticationType().equals(AuthenticationType.DATABASE)) {
            if (!customerNewDetails.getFullPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(customerNewDetails.getFullPassword());
                customerNewDetails.setFullPassword(encodedPassword);
            } else {
                customerNewDetails.setFullPassword(customerOldDetails.getFullPassword());
            }
        } else {
            customerNewDetails.setFullPassword(customerOldDetails.getFullPassword());
        }

        customerNewDetails.setEnabled(customerOldDetails.isEnabled());
        customerNewDetails.setCreatedTime(customerOldDetails.getCreatedTime());
        customerNewDetails.setVerificationCode(customerOldDetails.getVerificationCode());
        customerNewDetails.setAuthenticationType(customerOldDetails.getAuthenticationType());
        customerNewDetails.setResetPasswordToken(customerOldDetails.getResetPasswordToken());

        customerRepository.save(customerNewDetails);
    }

    public String updateResetPasswordToken(String email) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if (customer != null) {
            String token = RandomString.make(30);
            customer.setResetPasswordToken(token);
            customerRepository.save(customer);
            return token;
        } else {
            throw new CustomerNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public Customer getByResetPasswordToken(String token) {
        return customerRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(String token, String newPassword) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByResetPasswordToken(token);
        if (customer == null) {
            throw new CustomerNotFoundException("Invalid token. No customer found.");
        }
        customer.setFullPassword(newPassword);
        encodePassword(customer);
        customer.setResetPasswordToken(null);
        customerRepository.save(customer);
    }
}
