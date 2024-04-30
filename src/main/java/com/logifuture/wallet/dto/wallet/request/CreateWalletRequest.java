package com.logifuture.wallet.dto.wallet.request;


import com.logifuture.wallet.controller.rest.WalletRestController;
import jakarta.validation.constraints.NotNull;

/**
 * Bean which wrap rest request body of
 * {@link WalletRestController#createWallet(CreateWalletRequest)}
 *
 * @param userId User identifier
 */
public record CreateWalletRequest(@NotNull(message = "User ID must not be null or empty") Long userId) {
}