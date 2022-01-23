package com.gearz.checkout.paypal;

public class PaypalApiException extends Exception {
    private static final long serialVersionUID = 1L;

    public PaypalApiException(String message) {
        super(message);
    }
}
