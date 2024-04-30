package com.logifuture.wallet.service.wallet;

import com.logifuture.wallet.entitity.User;
import com.logifuture.wallet.entitity.Wallet;
import com.logifuture.wallet.exceptions.user.UserNotFoundException;
import com.logifuture.wallet.exceptions.wallet.UnauthorizedUserForWallet;
import com.logifuture.wallet.exceptions.wallet.WalletNotExists;
import com.logifuture.wallet.repository.WalletRepository;
import com.logifuture.wallet.service.user.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Base class for wallet-related services providing common functionality.
 */
@Slf4j
public abstract class BaseWalletService {
    protected final WalletRepository walletRepository;
    protected final UserService userService;

    public BaseWalletService(WalletRepository walletRepository, UserService userService) {
        this.walletRepository = walletRepository;
        this.userService = userService;
    }

    /**
     * Retrieves the wallet based on the user ID and wallet ID, ensuring that the user is authorized to access the wallet.
     *
     * @param userId   The ID of the user.
     * @param walletId The ID of the wallet.
     * @return The wallet entity.
     * @throws WalletNotExists           if the wallet does not exist for the given user.
     * @throws UnauthorizedUserForWallet if the user is not authorized to access the wallet.
     * @throws UserNotFoundException if no user with the specified ID is found.
     */
    protected Wallet getWalletByUserIdAndWalletId(Long userId, Long walletId) {
        User fetchedUser = userService.findUserById(userId);
        Wallet fetchedWallet = walletRepository.findByUserId(fetchedUser.getId()).orElseThrow(() -> {
            log.warn("For the given userID:{} wallet does not exist", fetchedUser.getId());
            throw new WalletNotExists("User is not assigned to a wallet");
        });

        if (!Objects.equals(walletId, fetchedWallet.getId())) {
            log.warn("For the given userID:{} the assigned walletID is {} but the user gave walletID:{}", fetchedUser.getId(), fetchedWallet.getId(), walletId);
            throw new UnauthorizedUserForWallet("You cannot access a wallet that you are not assigned to");
        }
        return fetchedWallet;
    }
}