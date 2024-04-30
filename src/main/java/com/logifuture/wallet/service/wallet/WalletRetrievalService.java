package com.logifuture.wallet.service.wallet;

import com.logifuture.wallet.dto.wallet.request.GetWalletRequest;
import com.logifuture.wallet.dto.wallet.response.GetWalletResponse;
import com.logifuture.wallet.entitity.Wallet;
import com.logifuture.wallet.exceptions.user.UserNotFoundException;
import com.logifuture.wallet.exceptions.wallet.UnauthorizedUserForWallet;
import com.logifuture.wallet.exceptions.wallet.WalletIsNotAssignedToUser;
import com.logifuture.wallet.mapper.WalletMapper;
import com.logifuture.wallet.repository.WalletRepository;
import com.logifuture.wallet.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class for retrieving details of wallets.
 */
@Service
@Slf4j
public class WalletRetrievalService extends BaseWalletService {
    private final WalletMapper walletMapper;

    public WalletRetrievalService(WalletRepository walletRepository, UserService userService, WalletMapper walletMapper) {
        super(walletRepository, userService);
        this.walletMapper = walletMapper;
    }

    /**
     * Retrieves details of the wallet identified by the wallet ID and user ID specified in the request.
     *
     * @param walletId         The ID of the wallet to retrieve details for.
     * @param getWalletRequest The request containing the user ID.
     * @throws WalletIsNotAssignedToUser           if the wallet does not exist for the given user.
     * @throws UnauthorizedUserForWallet if the user is not authorized to access the wallet.
     * @throws UserNotFoundException if no user with the specified ID is found.
     * @return A response containing details of the requested wallet.
     */
    public GetWalletResponse getWallet(Long walletId, GetWalletRequest getWalletRequest) {
        Wallet fetchedWallet = getWalletByUserIdAndWalletId(getWalletRequest.userId(),walletId);
        return walletMapper.walletToGetWalletResponse(fetchedWallet);
    }
}