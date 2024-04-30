package com.logifuture.wallet.exceptions.wallet;

public class WalletAlreadyExists extends  RuntimeException{
    public WalletAlreadyExists(String message) {
        super(message);
    }

    public WalletAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
}