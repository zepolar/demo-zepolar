package com.zepolar.demo.wallet.exception;

public class WalletNotFoundException extends RuntimeException{

    public WalletNotFoundException(String message) {
        super(message);
    }

    public WalletNotFoundException(Throwable cause) {
        super(cause);
    }
}
