package com.gearz.dto;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class CheckoutInfomation {

    @Getter
    @Setter
    private float productCost;

    @Getter
    @Setter
    private float productTotal;

    @Getter
    @Setter
    private float shippingCostTotal;

    @Getter
    @Setter
    private float paymentTotal;

    @Getter
    @Setter
    private int estimatedDeliveryDays;

    @Getter
    @Setter
    private boolean codSupported;

    public Date getDeliveryCompleteDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, estimatedDeliveryDays);
        return calendar.getTime();
    }

    public String getPaymentTotalForPayPal() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
        return decimalFormat.format(paymentTotal);
    }
}
