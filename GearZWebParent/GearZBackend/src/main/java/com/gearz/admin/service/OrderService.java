package com.gearz.admin.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import com.gearz.admin.repository.CityRepository;
import com.gearz.admin.repository.DistrictRepository;
import com.gearz.admin.repository.OrderRepository;
import com.gearz.admin.repository.WardRepository;
import com.gearz.common.entity.City;
import com.gearz.common.entity.District;
import com.gearz.common.entity.Order;
import com.gearz.common.entity.OrderStatus;
import com.gearz.common.entity.OrderTracking;
import com.gearz.common.entity.Ward;
import com.gearz.common.exception.OrderNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private WardRepository wardRepository;

    public List<Order> listAllOrders() {
        return orderRepository.findAll();
    }

    public List<City> listAllCities() {
        return cityRepository.findAllByOrderByNameAsc();
    }

    public List<District> listAllDistrictsBasedOnCityInOrder(Order order) {
        return districtRepository.findByCityOrderByNameAsc(cityRepository.findByName(order.getCity()));
    }

    public List<Ward> listAllWardsBasedOnDistrictInOrder(Order order) {
        return wardRepository.findByDistrictOrderByNameAsc(districtRepository.findByName(order.getDistrict()));
    }

    public Order get(Integer id) throws OrderNotFoundException {
        try {
            return orderRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new OrderNotFoundException("Could not find any order with ID " + id);
        }
    }

    public void delete(Integer id) throws OrderNotFoundException {
        Long count = orderRepository.countById(id);
        if (count == null || count == 0) {
            throw new OrderNotFoundException("Could not find any order with ID " + id);
        }
        orderRepository.deleteById(id);
    }

    public void save(Order orderInForm) {
        Order orderInDB = orderRepository.findById(orderInForm.getId()).get();
        orderInForm.setOrderTime(orderInDB.getOrderTime());
        orderInForm.setCustomer(orderInDB.getCustomer());

        orderRepository.save(orderInForm);
    }

    public void updateStatus(Integer orderId, String status) {
        Order orderInDB = orderRepository.findById(orderId).get();
        OrderStatus status2Update = OrderStatus.valueOf(status);

        if (!orderInDB.hasStatus(status2Update)) {
            List<OrderTracking> orderTrackings = orderInDB.getOrderTrackings();
            OrderTracking tracking = new OrderTracking();
            tracking.setStatus(status2Update);
            tracking.setUpdatedTime(new Date());
            tracking.setStatusDetail(status2Update.defaultDescription());
            tracking.setOrder(orderInDB);

            orderTrackings.add(tracking);
            orderInDB.setStatus(status2Update);
            orderRepository.save(orderInDB);
        }
    }
}
