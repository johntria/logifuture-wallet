package com.logifuture.wallet.service.wallet;

import com.logifuture.wallet.dto.wallet.request.CreateWalletRequest;
import com.logifuture.wallet.dto.wallet.response.CreateWalletResponse;
import com.logifuture.wallet.entitity.StateOfWallet;
import com.logifuture.wallet.entitity.User;
import com.logifuture.wallet.entitity.Wallet;
import com.logifuture.wallet.exceptions.user.UserNotFoundException;
import com.logifuture.wallet.exceptions.wallet.WalletAlreadyExists;
import com.logifuture.wallet.repository.UserRepository;
import com.logifuture.wallet.repository.WalletRepository;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Random;

/**
 * IT test for {@link com.logifuture.wallet.service.wallet.WalletCreationService }
 */
@Testcontainers
@SpringBootTest
class WalletCreationServiceITTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletCreationService underTest;

    User userWithWallet;
    Wallet walletOfUser;
    User userWithOutWallet;

    @BeforeEach
    void setup() {
        User user = new User();
        user.setFirstName("test");
        user.setLastName("test");
        userWithWallet = userRepository.save(user);
        User user2 = new User();
        user2.setFirstName("test2");
        user2.setLastName("test2");
        userWithOutWallet = userRepository.save(user2);
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setState(StateOfWallet.AVAILABLE_BALANCE);
        wallet.setUser(userWithWallet);
        walletOfUser = walletRepository.save(wallet);
    }

    @AfterEach()
    void cleanUp() {
        walletRepository.delete(walletOfUser);
        userRepository.delete(userWithWallet);
        userRepository.delete(userWithOutWallet);
    }

    /**
     * IT test for {@link WalletCreationService#createWallet(CreateWalletRequest)}
     * GIVEN: A User {@link this#userWithOutWallet} which exist but without wallet
     * EXPECTED: Wallet to be created
     */
    @Test
    void createWallet_given_existing_user_without_wallet_expected_success_creation() {
        CreateWalletResponse wallet = underTest.createWallet(new CreateWalletRequest(userWithOutWallet.getId()));
        assertEquals(BigDecimal.ZERO, wallet.balance());
        assertEquals(StateOfWallet.AVAILABLE_BALANCE, wallet.currentState());
        assertNotNull(wallet.walletId());
        walletRepository.deleteById(wallet.walletId());
    }

    /**
     * IT test for {@link WalletCreationService#createWallet(CreateWalletRequest)}
     * GIVEN: A User {@link this#userWithOutWallet} which exist but with wallet
     * EXPECTED: {@link WalletAlreadyExists}
     */
    @Test
    void createWallet_given_existing_user_with_wallet_expected_exception() {
        WalletAlreadyExists walletAlreadyExists = assertThrows(WalletAlreadyExists.class, () -> underTest.createWallet(new CreateWalletRequest(userWithWallet.getId())));
        assertEquals("User already has a wallet", walletAlreadyExists.getMessage());
    }

    /**
     * IT test for {@link WalletCreationService#createWallet(CreateWalletRequest)}
     * GIVEN: A User {@link this#userWithOutWallet} which exist but with wallet
     * EXPECTED: {@link WalletAlreadyExists}
     */
    @Test
    void createWallet_given_non_existing_user_expected_exception() {

        Long userID = new Random().nextLong();
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> underTest.createWallet(new CreateWalletRequest(userID)));
        assertEquals("User with id:" + userID + " not found", userNotFoundException.getMessage());
    }
}