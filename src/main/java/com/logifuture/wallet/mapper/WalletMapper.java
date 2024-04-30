package com.logifuture.wallet.mapper;

import com.logifuture.wallet.dto.wallet.response.CreateWalletResponse;
import com.logifuture.wallet.dto.wallet.response.GetWalletResponse;
import com.logifuture.wallet.entitity.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {
    public CreateWalletResponse walletToCreateWalletResponse(Wallet wallet) {
        return new CreateWalletResponse(wallet.getId(), wallet.getBalance(), wallet.getState());
    }
    public GetWalletResponse walletToGetWalletResponse(Wallet wallet) {
        return new GetWalletResponse(wallet.getId(), wallet.getBalance(), wallet.getState());
    }
}