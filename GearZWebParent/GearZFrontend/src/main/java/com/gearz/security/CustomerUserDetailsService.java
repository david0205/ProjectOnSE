package com.gearz.security;

import com.gearz.common.entity.Customer;
import com.gearz.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = repo.findByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException("Customer " + email + " does not exist");
        }
        return new CustomerUserDetails(customer);
    }

}
