package com.gearz.admin.user;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import com.gearz.admin.repository.OrderRepository;
import com.gearz.common.entity.Customer;
import com.gearz.common.entity.Order;
import com.gearz.common.entity.OrderDetail;
import com.gearz.common.entity.OrderStatus;
import com.gearz.common.entity.OrderTracking;
import com.gearz.common.entity.PaymentMethod;
import com.gearz.common.entity.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class OrderTest {

    @Autowired
    private OrderRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testAddNewOrder() {
        Customer customer = entityManager.find(Customer.class, 7);
        Product product = entityManager.find(Product.class, 1);
        Order order = new Order();

        order.setOrderTime(new Date());
        order.setCustomer(customer);
        order.getFullAddressFromCustomer();

        order.setShippingCost(10);
        order.setTax(0);
        order.setSubtotal(product.getDiscountedPrice());
        order.setTotal(product.getDiscountedPrice() + 10);

        order.setPaymentMethod(PaymentMethod.COD);
        order.setStatus(OrderStatus.NEW);
        order.setEstimatedDeliveryDays(1);
        order.setDeliveryCompleteDate(new Date());

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setOrder(order);
        orderDetail.setShippingCost(10);
        orderDetail.setQuantity(1);
        orderDetail.setSubtotal(product.getDiscountedPrice());
        orderDetail.setUnitPrice(product.getDiscountedPrice());

        order.getOrderDetails().add(orderDetail);

        Order savedOrder = repo.save(order);
        assertThat(savedOrder.getId()).isGreaterThan(0);
    }

    @Test
    public void testUpdateOrderTracking() {
        Integer orderId = 1;
        Order order = repo.findById(orderId).get();

        OrderTracking newTracking = new OrderTracking();
        newTracking.setOrder(order);
        newTracking.setUpdatedTime(new Date());
        newTracking.setStatus(OrderStatus.NEW);
        newTracking.setStatusDetail(OrderStatus.NEW.defaultDescription());

        List<OrderTracking> orderTrackings = order.getOrderTrackings();
        orderTrackings.add(newTracking);

        Order updatedOrder = repo.save(order);

        assertThat(updatedOrder.getOrderTrackings()).hasSizeGreaterThan(1);
    }
}
