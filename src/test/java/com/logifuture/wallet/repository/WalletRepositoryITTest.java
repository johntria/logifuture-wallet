package com.logifuture.wallet.repository;

import com.logifuture.wallet.entitity.StateOfWallet;
import com.logifuture.wallet.entitity.User;
import com.logifuture.wallet.entitity.Wallet;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * IT test for {@link WalletRepository }
 */
@Testcontainers
@SpringBootTest
class WalletRepositoryITTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserRepository userRepository;

    Long walletId;
    Long userId;


    @BeforeEach
    void setUp() {
        setUpUser();
        setUpWallet();
    }

    @AfterEach
    void tearDown() {
        walletRepository.deleteById(walletId);
        userRepository.deleteById(userId);

    }

    /**
     * IT test for {@link WalletRepository#findByUserId}
     * GIVEN: User id which already exists and assigned to wallet
     * EXPECTED: To fetch wallet
     */
    @Test()
    void testFindByUserId_given_existing_user_with_assigned_wallet_expected_fetch_success() {
        Optional<Wallet> optionalWallet = walletRepository.findByUserId(userId);
        assertTrue(optionalWallet.isPresent());
    }


    private void setUpWallet() {
        Wallet wallet = new Wallet();
        wallet.setState(StateOfWallet.AVAILABLE_BALANCE);
        wallet.setBalance(BigDecimal.valueOf(12.12));
        User user = userRepository.findById(userId).orElse(null);
        wallet.setUser(user);
        walletId = walletRepository.save(wallet).getId();
    }

    private void setUpUser() {
        User user = new User();
        user.setFirstName("first");
        user.setLastName("last");
        User savedUser = userRepository.save(user);
        userId = savedUser.getId();
    }
}