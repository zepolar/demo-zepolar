package com.zepolar.demo.wallet.exception;

public class WalletInternalException extends RuntimeException{

    public WalletInternalException(String message) {
        super(message);
    }

    public WalletInternalException(Throwable cause) {
        super(cause);
    }
}
