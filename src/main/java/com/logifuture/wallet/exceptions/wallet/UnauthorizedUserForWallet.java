package com.logifuture.wallet.exceptions.wallet;

public class UnauthorizedUserForWallet extends RuntimeException{
    public UnauthorizedUserForWallet() {
    }

    public UnauthorizedUserForWallet(String message) {
        super(message);
    }
}