package com.gearz.repository;

import com.gearz.common.entity.AuthenticationType;
import com.gearz.common.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    public Customer findByEmail(String email);

    public Customer findByVerificationCode(String verificationCode);

    @Query("UPDATE Customer c SET c.enabled = true, c.verificationCode = null WHERE c.id = ?1")
    @Modifying
    public void enable(Integer id);

    @Query("UPDATE Customer c SET c.authenticationType = ?2 WHERE c.id = ?1")
    @Modifying
    public void updateAuthenticationType(Integer customerId, AuthenticationType authType);

    public Customer findByResetPasswordToken(String token);
}
