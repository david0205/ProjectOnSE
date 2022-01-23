package com.gearz.repository;

import java.util.List;

import com.gearz.common.entity.Customer;
import com.gearz.common.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.customer.id = ?1")
    public List<Order> findAll(Integer customerId);

    public Order findByIdAndCustomer(Integer id, Customer customer);
}
