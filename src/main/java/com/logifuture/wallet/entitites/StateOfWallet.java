package com.logifuture.wallet.entitites;

/**
 * States of {@link Wallet}
 */
public enum StateOfWallet {
    /**
     * The amount of funds currently available in the wallet that can be used for betting or other transactions.
     */
    AVAILABLE_BALANCE,
    /**
     * The total amount of funds in the wallet, including any pending transactions or bonuses that have not yet been released.
     */
    TOTAL_BALANCE,
    /**
     * The portion of the wallet balance that is currently unavailable for use due to ongoing transactions, such as pending bets or withdrawals.
     */
    LOCKED_BALANCE
}