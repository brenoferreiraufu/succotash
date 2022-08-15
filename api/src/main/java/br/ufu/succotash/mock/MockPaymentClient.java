package br.ufu.succotash.mock;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MockPaymentClient {
    private final BigDecimal mockTest = new BigDecimal(1000);

    public HttpStatus pay(MockPaymentRequest paymentRequest) {
        if (paymentRequest.total().compareTo(mockTest) > 0) {
            return HttpStatus.BAD_REQUEST;
        }

        return HttpStatus.OK;
    }
}
