package com.logifuture.wallet.exceptions.wallet;

public class WalletNotExists extends RuntimeException{
    public WalletNotExists() {
    }

    public WalletNotExists(String message) {
        super(message);
    }
}