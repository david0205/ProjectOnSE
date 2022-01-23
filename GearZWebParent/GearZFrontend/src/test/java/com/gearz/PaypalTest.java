package com.gearz;

import java.util.Arrays;

import com.gearz.checkout.paypal.PaypalOrderResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PaypalTest {
    private static final String BASE_URL = "https://api-m.sandbox.paypal.com";
    private static final String GET_ORDER_API = "/v2/checkout/orders/";
    private static final String CLIENT_ID = "AcrO6c2vvJE0EmwA73s0spdq8Sp0Tjn3kAI6tXdwE0Xlyfyhx-xg8blhlXQ6-AfYScvz9EemYuDUxClA";
    private static final String CLIENT_SECRET = "EH0zbohjhRvFve8LMgCA7k8X052O1o9ybvvYfk_weQAeGb_5gbHvnPSIiIfaRtyk43Gh_bCzibl2l6iL";

    @Test
    public void testGetOrderDetails() {
        String orderid = "8YE44486HH390022D";
        String requestURL = BASE_URL + GET_ORDER_API + orderid;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Accept-Languages", "en_US");
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PaypalOrderResponse> response = restTemplate.exchange(requestURL, HttpMethod.GET, request,
                PaypalOrderResponse.class);

        PaypalOrderResponse orderResponse = response.getBody();
        System.out.println(orderResponse.getId());
        System.out.println(orderResponse.validate(orderid));
    }
}
