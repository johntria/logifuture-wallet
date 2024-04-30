package com.logifuture.wallet.service.wallet;

import com.logifuture.wallet.dto.wallet.request.AddFundsToWalletRequest;
import com.logifuture.wallet.dto.wallet.request.RemoveFundsFromWalletRequest;
import com.logifuture.wallet.entitity.StateOfWallet;
import static com.logifuture.wallet.entitity.StateOfWallet.LOCKED_BALANCE;
import static com.logifuture.wallet.entitity.StateOfWallet.TOTAL_BALANCE;
import com.logifuture.wallet.entitity.User;
import com.logifuture.wallet.entitity.Wallet;
import com.logifuture.wallet.exceptions.wallet.WalletBalanceException;
import com.logifuture.wallet.exceptions.wallet.WalletStateException;
import com.logifuture.wallet.repository.UserRepository;
import com.logifuture.wallet.repository.WalletRepository;
import com.logifuture.wallet.service.user.UserService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import java.math.BigInteger;
import java.util.Optional;

/**
 * IT for {@link WalletFundsManagementService }
 */
@Testcontainers
@SpringBootTest
class WalletFundsManagementServiceITTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    WalletRepository walletRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Autowired
    WalletFundsManagementService underTest;

    User userWithWallet;
    Wallet walletOfUser;

    @BeforeEach
    void setup() {
        User user = new User();
        user.setFirstName("test");
        user.setLastName("test");
        userWithWallet = userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setState(StateOfWallet.AVAILABLE_BALANCE);
        wallet.setUser(userWithWallet);
        walletOfUser = walletRepository.save(wallet);
    }

    /**
     * IT test for {@link WalletFundsManagementService#addFunds(Long, AddFundsToWalletRequest)}
     * Given: Wallet id with User id related but not allowed status
     * Expected : {@link WalletStateException}
     */
    @Test
    void to_be_added_given_wallet_id_user_id_which_exists_but_not_allowed_wallet_status_expected_exception(){
        walletOfUser.setState(LOCKED_BALANCE);
        Wallet givenNotAllowedStatusInOrderToAddFound = walletRepository.save(walletOfUser);
        AddFundsToWalletRequest given = new AddFundsToWalletRequest(userWithWallet.getId(), BigDecimal.TEN);
        WalletStateException walletStateException = assertThrows(WalletStateException.class, () -> underTest.addFunds(givenNotAllowedStatusInOrderToAddFound.getId(), given));
        assertEquals("Operation not allowed for wallet in state: LOCKED_BALANCE",walletStateException.getMessage());
    }

    /**
     * IT test for {@link WalletFundsManagementService#addFunds(Long, AddFundsToWalletRequest)}
     * Given: Wallet id with User id related with allowed status
     * Expected : To be updated balance .
     */
    @Test
    void to_be_added_given_wallet_id_user_id_which_exists_expected_to_be_updated(){
        AddFundsToWalletRequest given = new AddFundsToWalletRequest(userWithWallet.getId(), BigDecimal.TEN);
        underTest.addFunds(walletOfUser.getId(), given);
        Optional<Wallet> updatedValue = walletRepository.findById(walletOfUser.getId());
        assertTrue(updatedValue.isPresent());
        assertEquals(BigDecimal.TEN.toBigInteger(),updatedValue.get().getBalance().toBigInteger());
    }

    /**
     * IT test for {@link WalletFundsManagementService#removeFunds(Long, RemoveFundsFromWalletRequest)}
     * Given: Wallet id with User id related but not allowed status
     * Expected : {@link WalletStateException}
     */
    @Test
    void to_be_removed_given_wallet_id_user_id_which_exists_but_not_allowed_wallet_status_expected_exception(){
        walletOfUser.setState(LOCKED_BALANCE);
        Wallet givenNotAllowedStatusInOrderToAddFound = walletRepository.save(walletOfUser);
        RemoveFundsFromWalletRequest given = new RemoveFundsFromWalletRequest(userWithWallet.getId(), BigDecimal.TEN);
        WalletStateException walletStateException = assertThrows(WalletStateException.class, () -> underTest.removeFunds(givenNotAllowedStatusInOrderToAddFound.getId(), given));
        assertEquals("Operation not allowed for wallet in state: LOCKED_BALANCE",walletStateException.getMessage());
    }
    /**
     * IT test for {@link WalletFundsManagementService#removeFunds(Long, RemoveFundsFromWalletRequest)}
     * Given: Wallet id with User id related but not allowed status
     * Expected : {@link WalletStateException}
     */
    @Test
    void to_be_removed_given_wallet_id_user_id_which_exists_but_not_allowed_wallet_status_expected_exception_v2(){
        walletOfUser.setState(TOTAL_BALANCE);
        Wallet givenNotAllowedStatusInOrderToAddFound = walletRepository.save(walletOfUser);
        RemoveFundsFromWalletRequest given = new RemoveFundsFromWalletRequest(userWithWallet.getId(), BigDecimal.TEN);
        WalletStateException walletStateException = assertThrows(WalletStateException.class, () -> underTest.removeFunds(givenNotAllowedStatusInOrderToAddFound.getId(), given));
        assertEquals("Operation not allowed for wallet in state: TOTAL_BALANCE",walletStateException.getMessage());
    }

    /**
     * IT test for {@link WalletFundsManagementService#addFunds(Long, AddFundsToWalletRequest)}
     * Given: Wallet id with User id related with allowed status but not enough balance
     * Expected : {@link WalletBalanceException}
     */
    @Test
    void to_be_removed_given_wallet_id_user_id_which_exists_not_enough_balance_expected_exception(){
        RemoveFundsFromWalletRequest given = new RemoveFundsFromWalletRequest(userWithWallet.getId(), BigDecimal.TEN);
        WalletBalanceException walletStateException = assertThrows(WalletBalanceException.class, () -> underTest.removeFunds(walletOfUser.getId(), given));
        assertEquals("You cannot withdrawal balance. Total balance:0.00 Withdrawal request:10",walletStateException.getMessage());
    }

    /**
     * IT test for {@link WalletFundsManagementService#addFunds(Long, AddFundsToWalletRequest)}
     * Given: Wallet id with User id related with allowed status but not enough balance
     * Expected : {@link WalletBalanceException}
     */
    @Test
    void to_be_removed_given_wallet_id_user_id_which_exists_enough_balance_expected_to_take_money(){
        walletOfUser.setBalance(BigDecimal.TEN);
        walletRepository.save(walletOfUser);
        RemoveFundsFromWalletRequest given = new RemoveFundsFromWalletRequest(userWithWallet.getId(), BigDecimal.TWO);
        underTest.removeFunds(walletOfUser.getId(), given);
        Optional<Wallet> byId = walletRepository.findById(walletOfUser.getId());
        assertTrue(byId.isPresent());
        assertEquals(BigInteger.valueOf(8),byId.get().getBalance().toBigInteger());
    }

    /**
     * IT test for {@link WalletFundsManagementService#addFunds(Long, AddFundsToWalletRequest)}
     * Given: Wallet id with User id related with allowed status but not enough balance
     * Expected : {@link WalletBalanceException}
     */
    @Test
    void to_be_removed_given_wallet_id_user_id_which_exists_enough_balance_left_with_zero_expected_to_take_money(){
        walletOfUser.setBalance(BigDecimal.TEN);
        walletRepository.save(walletOfUser);
        RemoveFundsFromWalletRequest given = new RemoveFundsFromWalletRequest(userWithWallet.getId(), BigDecimal.TEN);
        underTest.removeFunds(walletOfUser.getId(), given);
        Optional<Wallet> byId = walletRepository.findById(walletOfUser.getId());
        assertTrue(byId.isPresent());
        assertEquals(BigInteger.valueOf(0),byId.get().getBalance().toBigInteger());
    }
}