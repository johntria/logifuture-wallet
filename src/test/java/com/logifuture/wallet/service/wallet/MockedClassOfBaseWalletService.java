package com.logifuture.wallet.service.wallet;

import com.logifuture.wallet.repository.WalletRepository;
import com.logifuture.wallet.service.user.UserService;

/**
 * Instance of {@link BaseWalletServiceITTest}
 */
sealed class MockedClassOfBaseWalletService extends BaseWalletService permits BaseWalletServiceITTest {
    public MockedClassOfBaseWalletService(WalletRepository walletRepository, UserService userService) {
        super(walletRepository, userService);
    }
}