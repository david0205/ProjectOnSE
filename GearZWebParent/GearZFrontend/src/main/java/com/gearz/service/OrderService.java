package com.gearz.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.gearz.common.entity.Address;
import com.gearz.common.entity.CartItem;
import com.gearz.common.entity.Customer;
import com.gearz.common.entity.Order;
import com.gearz.common.entity.OrderDetail;
import com.gearz.common.entity.OrderStatus;
import com.gearz.common.entity.OrderTracking;
import com.gearz.common.entity.PaymentMethod;
import com.gearz.common.entity.Product;
import com.gearz.common.exception.OrderNotFoundException;
import com.gearz.dto.CheckoutInfomation;
import com.gearz.dto.OrderReturnRequest;
import com.gearz.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Customer customer, Address address, List<CartItem> cartItems, PaymentMethod paymentMethod,
            CheckoutInfomation checkoutInfo) {
        Order newOrder = new Order();
        newOrder.setOrderTime(new Date());
        if (paymentMethod.equals(PaymentMethod.PAYPAL)) {
            newOrder.setStatus(OrderStatus.PAID);
        } else {
            newOrder.setStatus(OrderStatus.NEW);
        }
        newOrder.setCustomer(customer);
        newOrder.setProductCost(checkoutInfo.getProductCost());
        newOrder.setSubtotal(checkoutInfo.getProductTotal());
        newOrder.setShippingCost(checkoutInfo.getShippingCostTotal());
        newOrder.setTax(0.0F);
        newOrder.setTotal(checkoutInfo.getPaymentTotal());
        newOrder.setPaymentMethod(paymentMethod);
        newOrder.setEstimatedDeliveryDays(checkoutInfo.getEstimatedDeliveryDays());
        newOrder.setDeliveryCompleteDate(checkoutInfo.getDeliveryCompleteDate());

        if (address == null) {
            newOrder.getFullAddressFromCustomer();
        } else {
            newOrder.getShippingAddress(address);
        }

        Set<OrderDetail> orderDetails = newOrder.getOrderDetails();
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(newOrder);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setUnitPrice(product.getDiscountedPrice());
            orderDetail.setProductCost(((item.getProduct().getDiscountedPrice() * 95) / 100));
            orderDetail.setSubtotal(item.getSubtotal());
            orderDetail.setShippingCost(item.getShippingCost());

            orderDetails.add(orderDetail);
        }

        List<OrderTracking> orderTrackings = newOrder.getOrderTrackings();
        orderTrackings
                .add(new OrderTracking(OrderStatus.NEW.defaultDescription(), new Date(), OrderStatus.NEW, newOrder));
        if (paymentMethod.equals(PaymentMethod.PAYPAL)) {
            orderTrackings.add(
                    new OrderTracking(OrderStatus.PAID.defaultDescription(), new Date(), OrderStatus.PAID, newOrder));
        }

        return orderRepository.save(newOrder);
    }

    public List<Order> listAllOrderForCustomer(Customer customer) {
        return orderRepository.findAll(customer.getId());
    }

    public Order getOrder(Integer id, Customer customer) {
        return orderRepository.findByIdAndCustomer(id, customer);
    }

    public void setOrderReturnRequested(OrderReturnRequest request, Customer customer) throws OrderNotFoundException {
        Order order = orderRepository.findByIdAndCustomer(request.getOrderId(), customer);
        if (order == null) {
            throw new OrderNotFoundException("Order ID " + request.getOrderId() + " not found");
        }
        if (order.isReturnRequested()) {
            return;
        }
        OrderTracking tracking = new OrderTracking();
        tracking.setOrder(order);
        tracking.setUpdatedTime(new Date());
        tracking.setStatus(OrderStatus.RETURN_REQUESTED);

        String notes = "Reason: " + request.getReason();
        if (!"".equals(request.getAdditionalInfo())) {
            notes += ". " + request.getAdditionalInfo();
        }
        tracking.setStatusDetail(notes);

        order.getOrderTrackings().add(tracking);
        order.setStatus(OrderStatus.RETURN_REQUESTED);

        orderRepository.save(order);
    }
}
