package com.logifuture.wallet.dto.wallet.request;

import com.logifuture.wallet.controller.rest.WalletRestController;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Bean which wrap rest request body of {@link WalletRestController#removeFundsFromWallet(Long, RemoveFundsFromWalletRequest)}
 *
 * @param userId User identifier
 * @param amount Amount which will be removed from wallet
 */
public record RemoveFundsFromWalletRequest(@NotNull(message = "User ID must not be null or empty") Long userId,
                                           @DecimalMin(message = "Amount must be greater than 0",value = "0.0") BigDecimal amount
) {
}