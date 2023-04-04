package com.zepolar.demo.wallet.exception;

public class WalletBodyException extends RuntimeException{
    public WalletBodyException(String message) {
        super(message);
    }

    public WalletBodyException(Throwable cause) {
        super(cause);
    }
}
