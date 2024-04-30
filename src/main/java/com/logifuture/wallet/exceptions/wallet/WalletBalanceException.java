package com.logifuture.wallet.exceptions.wallet;

public class WalletBalanceException extends RuntimeException{
    public WalletBalanceException() {
    }

    public WalletBalanceException(String message) {
        super(message);
    }
}