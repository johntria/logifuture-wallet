package com.logifuture.wallet.service.wallet;

import com.logifuture.wallet.dto.wallet.request.AddFundsToWalletRequest;
import com.logifuture.wallet.dto.wallet.request.RemoveFundsFromWalletRequest;
import com.logifuture.wallet.entitity.StateOfWallet;
import static com.logifuture.wallet.entitity.StateOfWallet.checkForNotAllowedWalletState;
import com.logifuture.wallet.entitity.Wallet;
import com.logifuture.wallet.exceptions.user.UserNotFoundException;
import com.logifuture.wallet.exceptions.wallet.UnauthorizedUserForWallet;
import com.logifuture.wallet.exceptions.wallet.WalletBalanceException;
import com.logifuture.wallet.exceptions.wallet.WalletNotExists;
import com.logifuture.wallet.repository.WalletRepository;
import com.logifuture.wallet.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service class for managing funds in wallets.
 */
@Service
@Slf4j
public class WalletFundsManagementService extends BaseWalletService {

    private final WalletRepository walletRepository;

    public WalletFundsManagementService(WalletRepository walletRepository, UserService userService) {
        super(walletRepository, userService);
        this.walletRepository = walletRepository;
    }


    /**
     * Adds funds to the wallet identified by the wallet ID and user ID specified in the request.
     *
     * @param walletId                The ID of the wallet to which funds will be added.
     * @param addFundsToWalletRequest The request containing the amount of funds to add and the user ID.
     * @throws UserNotFoundException if no user with the specified ID is found.
     * @throws WalletNotExists           if the wallet does not exist for the given user.
     * @throws UnauthorizedUserForWallet if the user is not authorized to access the wallet.
     * @throws UserNotFoundException if no user with the specified ID is found.
     */
    public void addFunds(Long walletId, AddFundsToWalletRequest addFundsToWalletRequest) {
        Wallet fetchedWallet = getWalletByUserIdAndWalletId(addFundsToWalletRequest.userId(), walletId);
        checkForNotAllowedWalletState(fetchedWallet.getState(), StateOfWallet.LOCKED_BALANCE);
        fetchedWallet.setBalance(fetchedWallet.getBalance().add(addFundsToWalletRequest.amount()));
        Wallet updatedWallet = walletRepository.save(fetchedWallet);
        log.info("Added {} in  funds for userID:{} with walletID:{}.", addFundsToWalletRequest.amount(), addFundsToWalletRequest.userId(), updatedWallet.getId());
    }

    /**
     * Removes funds from the wallet identified by the wallet ID and user ID specified in the request.
     *
     * @param walletId                     The ID of the wallet from which funds will be removed.
     * @param removeFundsFromWalletRequest The request containing the amount of funds to remove and the user ID.
     * @throws WalletBalanceException if the withdrawal amount exceeds the available balance.
     */
    public void removeFunds(Long walletId, RemoveFundsFromWalletRequest removeFundsFromWalletRequest) {
        Wallet fetchedWallet = getWalletByUserIdAndWalletId(removeFundsFromWalletRequest.userId(), walletId);
        checkForNotAllowedWalletState(fetchedWallet.getState(), StateOfWallet.LOCKED_BALANCE, StateOfWallet.TOTAL_BALANCE);
        BigDecimal subtractedBalance = fetchedWallet.getBalance().subtract(removeFundsFromWalletRequest.amount());
        if (BigDecimal.ZERO.compareTo(subtractedBalance) > 0) {
            log.warn("UserID{} with walletID:{} tried to withdraw {} but total balance is {}", removeFundsFromWalletRequest.userId(), walletId, removeFundsFromWalletRequest.amount(), fetchedWallet.getBalance());
            throw new WalletBalanceException("You cannot withdrawal balance. Total balance:" + fetchedWallet.getBalance() + " Withdrawal request:" + removeFundsFromWalletRequest.amount());
        }
        fetchedWallet.setBalance(subtractedBalance);
        Wallet updatedWallet = walletRepository.save(fetchedWallet);
        log.info("Withdraw {} from funds for userID:{} with walletID:{}.", removeFundsFromWalletRequest.amount(), removeFundsFromWalletRequest.userId(), updatedWallet.getId());
    }
}