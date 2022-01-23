package com.gearz.admin.repository;

import com.gearz.common.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    public Long countById(Integer id);
}
