package com.logifuture.wallet.dto.wallet.request;

import com.logifuture.wallet.controller.rest.WalletRestController;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Bean which wrap rest request body of {@link WalletRestController#addFundsToWallet(Long, AddFundsToWalletRequest)}
 *
 * @param userId
 * @param amount
 */
public record AddFundsToWalletRequest(@NotNull(message = "User ID must not be null or empty") Long userId,
                                      @Positive(message = "Amount must be greater than 0") BigDecimal amount) {
}