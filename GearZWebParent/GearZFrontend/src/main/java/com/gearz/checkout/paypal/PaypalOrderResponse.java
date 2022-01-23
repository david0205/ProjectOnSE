package com.gearz.checkout.paypal;

import lombok.Getter;
import lombok.Setter;

public class PaypalOrderResponse {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String status;

    public boolean validate(String orderId) {
        return id.equals(orderId) && status.equals("COMPLETED");
    }
}
