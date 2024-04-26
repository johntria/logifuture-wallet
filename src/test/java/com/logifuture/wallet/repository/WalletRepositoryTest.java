package com.logifuture.wallet.repository;

import com.logifuture.wallet.entitites.StateOfWallet;
import com.logifuture.wallet.entitites.Wallet;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * IT test for {@link WalletRepository }
 */
@SpringBootTest
 class WalletRepositoryTest {
    Wallet savedWallet;

    @Autowired
    WalletRepository walletRepository;

    @BeforeEach
    void setUp() {
        Wallet wallet = new Wallet();
        wallet.setState(StateOfWallet.AVAILABLE_BALANCE);
        wallet.setBalance(BigDecimal.valueOf(12.12));
        savedWallet = walletRepository.save(wallet);
    }

    @AfterEach
    void tearDown() {
        walletRepository.delete(savedWallet);
    }

    @Test()
     void testFindById() {
        Optional<Wallet> optionalWallet = walletRepository.findById(savedWallet.getId());
        assertTrue(optionalWallet.isPresent());
        assertEquals(savedWallet.getState(), optionalWallet.get().getState());
        assertEquals(savedWallet.getBalance(), optionalWallet.get().getBalance());
    }
}