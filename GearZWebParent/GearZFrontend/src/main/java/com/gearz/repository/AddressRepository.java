package com.gearz.repository;

import java.util.List;

import com.gearz.common.entity.Address;
import com.gearz.common.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    public List<Address> findByCustomer(Customer customer);

    @Query("SELECT a FROM Address a WHERE a.id = ?1 AND a.customer.id = ?2")
    public Address findByIdAndCustomer(Integer addressId, Integer customerId);

    @Query("DELETE FROM Address a WHERE a.id = ?1 AND a.customer.id = ?2")
    @Modifying
    public void deleteByIdAndCustomer(Integer addressId, Integer customerId);

    @Query("UPDATE Address a SET a.defaultForShipping = true WHERE a.id = ?1")
    @Modifying
    public void setDefaultAddress(Integer id);

    @Query("UPDATE Address a SET a.defaultForShipping = false WHERE a.id != ?1 AND a.customer.id = ?2")
    @Modifying
    public void setNonDefaultForOtherAddresses(Integer defaultAddressId, Integer customerId);

    @Query("SELECT a FROM Address a WHERE a.customer.id = ?1 AND a.defaultForShipping = true")
    public Address findDefaultByCustomer(Integer customerId);
}
