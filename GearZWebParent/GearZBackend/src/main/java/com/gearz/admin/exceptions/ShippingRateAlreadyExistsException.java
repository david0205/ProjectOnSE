package com.gearz.admin.exceptions;

public class ShippingRateAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 1L;

    public ShippingRateAlreadyExistsException(String message) {
        super(message);
    }
}
