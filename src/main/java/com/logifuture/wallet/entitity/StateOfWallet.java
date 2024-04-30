package com.logifuture.wallet.entitity;

import com.logifuture.wallet.exceptions.wallet.WalletStateException;

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
    LOCKED_BALANCE;

    /**
     * Checks if the current state of a wallet matches any of the not allowed states.
     * If the current state matches any of the not allowed states, it throws a WalletStateException.
     *
     * @param currentState    The current state of the wallet.
     * @param notAllowedStates    Varargs representing the states that are not allowed.
     * @throws WalletStateException    If the current state matches any of the not allowed states.
     */
    public static void checkForNotAllowedWalletState(StateOfWallet currentState, StateOfWallet... notAllowedStates) {
        for (StateOfWallet allowedState : notAllowedStates) {
            if (allowedState.equals(currentState)) {
                throw new WalletStateException("Operation not allowed for wallet in state: " + currentState);
            }
        }
    }
}