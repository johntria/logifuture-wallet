package com.logifuture.wallet.exceptions.wallet;

public class WalletStateException  extends  RuntimeException{
    public WalletStateException() {
    }

    public WalletStateException(String message) {
        super(message);
    }
}