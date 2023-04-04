package com.zepolar.demo.payment.exception;

public class PaymentBodyException extends RuntimeException {
    public PaymentBodyException(String message) {
        super(message);
    }

    protected PaymentBodyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
