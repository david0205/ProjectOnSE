package com.gearz.checkout.paypal;

import java.util.Arrays;

import com.gearz.setting.PaymentSettingCollection;
import com.gearz.setting.SettingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class PaypalService {

    private static final String GET_ORDER_API = "/v2/checkout/orders/";

    @Autowired
    private SettingService settingService;

    public boolean validateOrder(String orderId) throws PaypalApiException {
        PaypalOrderResponse orderResponse = getOrderDetails(orderId);
        return orderResponse.validate(orderId);
    }

    private PaypalOrderResponse getOrderDetails(String orderId) throws PaypalApiException {
        ResponseEntity<PaypalOrderResponse> response = makeRequest(orderId);

        HttpStatus statusCode = response.getStatusCode();
        if (!statusCode.equals(HttpStatus.OK)) {
            throwResponseExceptions(statusCode);
        }
        return response.getBody();
    }

    private ResponseEntity<PaypalOrderResponse> makeRequest(String orderId) {
        PaymentSettingCollection paymentSettings = settingService.getPaymentSettings();
        String baseURL = paymentSettings.getURL();
        String requestURL = baseURL + GET_ORDER_API + orderId;
        String clientId = paymentSettings.getClientID();
        String clientSecret = paymentSettings.getClientSecret();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Accept-Languages", "en_US");
        headers.setBasicAuth(clientId, clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(requestURL, HttpMethod.GET, request, PaypalOrderResponse.class);
    }

    private void throwResponseExceptions(HttpStatus statusCode) throws PaypalApiException {
        String message = "";
        switch (statusCode) {
            case NOT_FOUND:
                message = "Order ID not found";
            case BAD_REQUEST:
                message = "Paypal checkout API thrown Bad Request";
            case INTERNAL_SERVER_ERROR:
                message = "Paypal server error";
            default:
                message = "Non-OK status code returned" + statusCode;
        }
        throw new PaypalApiException(message);
    }
}
