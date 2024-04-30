package com.logifuture.wallet.service.wallet;

import com.logifuture.wallet.dto.wallet.request.CreateWalletRequest;
import com.logifuture.wallet.dto.wallet.response.CreateWalletResponse;
import com.logifuture.wallet.entitity.StateOfWallet;
import com.logifuture.wallet.entitity.User;
import com.logifuture.wallet.entitity.Wallet;
import com.logifuture.wallet.exceptions.user.UserNotFoundException;
import com.logifuture.wallet.exceptions.wallet.UnauthorizedUserForWallet;
import com.logifuture.wallet.exceptions.wallet.WalletAlreadyExists;
import com.logifuture.wallet.exceptions.wallet.WalletNotExists;
import com.logifuture.wallet.mapper.WalletMapper;
import com.logifuture.wallet.repository.WalletRepository;
import com.logifuture.wallet.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Service class for creating wallets for users.
 */
@Service
@Slf4j
public class WalletCreationService extends BaseWalletService {
    private final WalletRepository walletRepository;
    private final UserService userService;
    private final WalletMapper walletMapper;

    public WalletCreationService(WalletRepository walletRepository, UserService userService, WalletMapper walletMapper) {
        super(walletRepository, userService);
        this.walletRepository = walletRepository;
        this.userService = userService;
        this.walletMapper = walletMapper;
    }


    /**
     * Creates a new wallet for the user specified in the request.
     *
     * @param createWalletRequest The request containing user ID for which the wallet needs to be created.
     * @return A response containing details of the created wallet.
     * @throws WalletAlreadyExists if the user already has a wallet.
     * @throws UserNotFoundException if no user with the specified ID is found.
     * @throws WalletNotExists           if the wallet does not exist for the given user.
     * @throws UnauthorizedUserForWallet if the user is not authorized to access the wallet.
     */
    public CreateWalletResponse createWallet(CreateWalletRequest createWalletRequest) {
        User fetchedUser = userService.findUserById(createWalletRequest.userId());
        Optional<Wallet> fetchedWallet = walletRepository.findByUserId(fetchedUser.getId());
        if (fetchedWallet.isPresent()) {
            log.warn("For the given userID:{} wallet already exists", createWalletRequest.userId());
            throw new WalletAlreadyExists("User already has a wallet");
        }

        Wallet toBeSaved = new Wallet();
        toBeSaved.setBalance(BigDecimal.ZERO);
        toBeSaved.setState(StateOfWallet.AVAILABLE_BALANCE);
        toBeSaved.setUser(fetchedUser);

        Wallet savedWallet = walletRepository.save(toBeSaved);
        log.info("Created wallet for user with id:{}", createWalletRequest.userId());
        return walletMapper.walletToCreateWalletResponse(savedWallet);
    }
}