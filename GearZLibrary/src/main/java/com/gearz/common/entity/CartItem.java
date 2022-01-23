package com.gearz.common.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_item")
@NoArgsConstructor
public class CartItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @Getter
    @Setter
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @Getter
    @Setter
    private Product product;

    @Getter
    @Setter
    private int quantity;

    @Setter
    @Transient
    private float shippingCost;

    @Transient
    public float getSubtotal() {
        return product.getDiscountedPrice() * quantity;
    }

    @Transient
    public float getShippingCost() {
        return shippingCost;
    }
}
