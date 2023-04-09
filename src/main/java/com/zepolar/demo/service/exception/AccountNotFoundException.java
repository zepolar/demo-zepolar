package com.zepolar.demo.service.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String message) {
        super(message);
    }
}
