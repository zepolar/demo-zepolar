package com.zepolar.demo.payment.exception;

public class PaymentServerError extends RuntimeException {

    public PaymentServerError(String message) {
        super(message);
    }

    public PaymentServerError(String message, Throwable cause) {
        super(message, cause);
    }
}
