package com.gearz.service;

import java.util.List;

import com.gearz.common.entity.CartItem;
import com.gearz.common.entity.Product;
import com.gearz.common.entity.ShippingRate;
import com.gearz.dto.CheckoutInfomation;

import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    private static final int DIM_DIVISOR = 139;  // https://www.fedex.com/content/dam/fedex/us-united-states/NNC/images/2019/Q4/DIM_diagram_2_922317903.jpg

    public CheckoutInfomation prepareCheckout(List<CartItem> cartItems, ShippingRate shippingRate) {
        CheckoutInfomation checkoutInfo = new CheckoutInfomation();

        float productCost = calculateTotalProductCost(cartItems);
        float productTotal = calculateProductTotal(cartItems);
        float shippingCostTotal = calculateShippingCost(cartItems, shippingRate);
        float paymentTotal = productTotal + shippingCostTotal;

        checkoutInfo.setProductCost(productCost);
        checkoutInfo.setProductTotal(productTotal);
        checkoutInfo.setShippingCostTotal(shippingCostTotal);
        checkoutInfo.setPaymentTotal(paymentTotal);
        checkoutInfo.setEstimatedDeliveryDays(shippingRate.getDays());
        checkoutInfo.setCodSupported(shippingRate.isCodSupported());

        return checkoutInfo;
    }

    private float calculateShippingCost(List<CartItem> cartItems, ShippingRate shippingRate) {
        float shippingCostTotal = 0.0F;

        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            float dimensionalWeight = (product.getLength() * product.getWidth() * product.getHeight()) / DIM_DIVISOR;
            float finalWeight = product.getWeight() > dimensionalWeight ? product.getWeight() : dimensionalWeight;
            float shippingCost = finalWeight * item.getQuantity() * shippingRate.getRate();

            item.setShippingCost(shippingCost);
            shippingCostTotal += shippingCost;
        }

        return shippingCostTotal;
    }

    private float calculateProductTotal(List<CartItem> cartItems) {
        float total = 0.0F;
        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }
        return total;
    }

    // Product cost refers to the costs incurred to create a product.
    // We did not think of this when planning.
    // So, by default, we decided to let 'product cost of 1 product = selling price * 95%'.
    // Product cost will be used for calculating revenue in Sales report.
    // Customer cannot see the product cost.
    private float calculateTotalProductCost(List<CartItem> cartItems) {
        float cost = 0.0F;
        for (CartItem item : cartItems) {
            cost += item.getQuantity() * ((item.getProduct().getDiscountedPrice() * 95) / 100);
        }
        return cost;
    }
}
