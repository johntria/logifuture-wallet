package com.logifuture.wallet.dto.wallet.response;

import com.logifuture.wallet.controller.rest.WalletRestController;
import com.logifuture.wallet.dto.wallet.request.RemoveFundsFromWalletRequest;
import com.logifuture.wallet.entitity.StateOfWallet;

import java.math.BigDecimal;
/**
 * Bean which wrap rest response body of {@link WalletRestController#removeFundsFromWallet(Long, RemoveFundsFromWalletRequest)}
 *
 * @param walletId     Wallet identifier
 * @param balance      Total balance of wallet
 * @param currentState Current state of wallet
 */
public record RemoveFundsFromWalletResponse(Long walletId, BigDecimal balance, StateOfWallet currentState) {
}