package com.logifuture.wallet.dto.wallet.response;

import com.logifuture.wallet.controller.rest.WalletRestController;
import com.logifuture.wallet.dto.wallet.request.GetWalletRequest;
import com.logifuture.wallet.entitity.StateOfWallet;

import java.math.BigDecimal;

/**
 * Bean which wrap rest response body of {@link WalletRestController#getWallet(Long, GetWalletRequest)}
 *
 * @param walletId     Wallet identifier
 * @param balance      Total balance of wallet
 * @param currentState Current state of wallet
 */
public record GetWalletResponse(Long walletId, BigDecimal balance, StateOfWallet currentState) {
}