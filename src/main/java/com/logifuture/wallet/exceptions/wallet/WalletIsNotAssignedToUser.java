package com.logifuture.wallet.exceptions.wallet;

public class WalletIsNotAssignedToUser extends RuntimeException{
    public WalletIsNotAssignedToUser() {
    }

    public WalletIsNotAssignedToUser(String message) {
        super(message);
    }
}