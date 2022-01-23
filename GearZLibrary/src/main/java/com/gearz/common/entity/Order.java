package com.gearz.common.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseAddressLine {

    @Column(nullable = false, length = 30)
    private String ward;

    @Column(nullable = false, length = 128)
    private String district;

    @Column(nullable = false, length = 30)
    private String city;

    private Date orderTime;

    private float productCost;

    private float shippingCost;

    private float subtotal;

    private float tax;

    private float total;

    private int estimatedDeliveryDays;

    private Date deliveryCompleteDate;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetail> orderDetails = new HashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("updatedTime ASC")
    private List<OrderTracking> orderTrackings = new ArrayList<>();

    public void getFullAddressFromCustomer() {
        setFullName(customer.getFullName());
        setPhoneNumber(customer.getPhoneNumber());
        setAddressLine(customer.getAddressLine());
        setWard(customer.getWard().getName());
        setDistrict(customer.getDistrict().getName());
        setCity(customer.getCity().getName());
    }

    @Transient
    public String getDestination() {
        String destination = ward + ", ";
        if (district != null && !district.isEmpty()) {
            destination += district + ", ";
        }
        destination += city;
        return destination;
    }

    public void getShippingAddress(Address address) {
        setFullName(address.getFullName());
        setPhoneNumber(address.getPhoneNumber());
        setAddressLine(address.getAddressLine());
        setWard(address.getWard().getName());
        setDistrict(address.getDistrict().getName());
        setCity(address.getCity().getName());
    }

    @Transient
    public String getShippingAddress() {
        String fullAddress = "";
        if (fullName != null && !fullName.isEmpty()) {
            fullAddress += "<strong>" + fullName + "</strong>";
        }
        if (!addressLine.isEmpty()) {
            fullAddress += ", " + addressLine;
        }
        if (ward != null && !ward.isEmpty()) {
            fullAddress += ", " + ward;
        }
        if (district != null && !district.isEmpty()) {
            fullAddress += ", " + district;
        }
        if (city != null && !city.isEmpty()) {
            fullAddress += ", " + city + ".<br>";
        }
        if (!phoneNumber.isEmpty()) {
            fullAddress += "- Telephone: " + phoneNumber;
        }

        return fullAddress;
    }

    @Transient
    public String getRecipientAddress() {
        String fullAddress = "";
        if (!addressLine.isEmpty()) {
            fullAddress += addressLine;
        }
        if (ward != null && !ward.isEmpty()) {
            fullAddress += ", " + ward;
        }
        if (district != null && !district.isEmpty()) {
            fullAddress += ", " + district;
        }
        if (city != null && !city.isEmpty()) {
            fullAddress += ", " + city + ".";
        }

        return fullAddress;
    }

    @Transient
    public String getDeliveryCompleteDateOnForm() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(this.deliveryCompleteDate);
    }

    public void setDeliveryCompleteDateOnForm(String date) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.deliveryCompleteDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Transient
    public boolean isCOD() {
        return paymentMethod.equals(PaymentMethod.COD);
    }

    @Transient
    public boolean isProcessing() {
        return hasStatus(OrderStatus.PROCESSING);
    }

    @Transient
    public boolean isPicked() {
        return hasStatus(OrderStatus.PICKED);
    }

    @Transient
    public boolean isShipping() {
        return hasStatus(OrderStatus.SHIPPING);
    }

    @Transient
    public boolean isDelivered() {
        return hasStatus(OrderStatus.DELIVERED);
    }

    @Transient
    public boolean isReturnRequested() {
        return hasStatus(OrderStatus.RETURN_REQUESTED);
    }

    @Transient
    public boolean isReturned() {
        return hasStatus(OrderStatus.RETURNED);
    }

    public boolean hasStatus(OrderStatus status) {
        for (OrderTracking tracking : orderTrackings) {
            if (tracking.getStatus().equals(status)) {
                return true;
            }
        }
        return false;
    }

    @Transient
    public String getProductNames() {
        String productNames = "<ul>";
        for (OrderDetail detail : orderDetails) {
            productNames += "<li>" + detail.getProduct().getShortName() + "</li>";
        }
        productNames += "</ul>";
        return productNames;
    }
}
