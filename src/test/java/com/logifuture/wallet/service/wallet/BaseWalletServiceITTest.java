package com.logifuture.wallet.service.wallet;

import com.logifuture.wallet.entitity.StateOfWallet;
import com.logifuture.wallet.entitity.User;
import com.logifuture.wallet.entitity.Wallet;
import com.logifuture.wallet.exceptions.user.UserNotFoundException;
import com.logifuture.wallet.exceptions.wallet.UnauthorizedUserForWallet;
import com.logifuture.wallet.exceptions.wallet.WalletNotExists;
import com.logifuture.wallet.repository.UserRepository;
import com.logifuture.wallet.repository.WalletRepository;
import com.logifuture.wallet.service.user.UserService;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
 * IT test for {@link BaseWalletService}
 */
@Testcontainers
@SpringBootTest
public final class BaseWalletServiceITTest extends MockedClassOfBaseWalletService {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");
    WalletRepository walletRepository;
    UserService userService;
    UserRepository userRepository;

    BaseWalletService underTest;
    User userWithWallet;
    Wallet walletOfUser;
    User userWithOutWallet;

    public BaseWalletServiceITTest(@Autowired WalletRepository walletRepository, @Autowired UserService userService, @Autowired UserRepository userRepository) {
        super(walletRepository, userService);
        this.walletRepository = walletRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setup() {
        underTest = new MockedClassOfBaseWalletService(walletRepository, userService);
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
     * IT test for {@link BaseWalletService#getWalletByUserIdAndWalletId(Long, Long)}
     * Given: Not existing userid with existing wallet id
     * Expected: {@link UserNotFoundException}
     */
    @Test
    void test_given_not_existing_user_id_with_existing_wallet_id_expected_exception() {
        long userId = new Random().nextLong();
        UserNotFoundException exc = assertThrows(UserNotFoundException.class, () -> underTest.getWalletByUserIdAndWalletId(userId, walletOfUser.getId()));
        assertEquals("User with id:" + userId + " not found", exc.getMessage());
    }

    /**
     * IT test for {@link BaseWalletService#getWalletByUserIdAndWalletId(Long, Long)}
     * Given: Existing userid without existing wallet id
     * Expected: {@link WalletNotExists}
     */
    @Test
    void test_given_existing_user_id_with_not_related_wallet_id_expected_exception() {
        WalletNotExists exc = assertThrows(WalletNotExists.class, () -> underTest.getWalletByUserIdAndWalletId(userWithOutWallet.getId(), walletOfUser.getId()));
        assertEquals("User is not assigned to a wallet", exc.getMessage());
    }

    /**
     * IT test for {@link BaseWalletService#getWalletByUserIdAndWalletId(Long, Long)}
     * Given: Existing userid with existing wallet id but not related to user
     * Expected: {@link UnauthorizedUserForWallet}
     */
    @Test
    void test_given_existing_user_id_with_wallet_id_but_not_related_to_given_wallet_id_expected_exception() {
        UnauthorizedUserForWallet exc = assertThrows(UnauthorizedUserForWallet.class, () -> underTest.getWalletByUserIdAndWalletId(userWithWallet.getId(), new Random().nextLong()));
        assertEquals("You cannot access a wallet that you are not assigned to", exc.getMessage());
    }

    /**
     * IT test for {@link BaseWalletService#getWalletByUserIdAndWalletId(Long, Long)}
     * Given: Existing userid with existing wallet which is related
     * Expected: Existing {@link Wallet}
     */
    @Test
    void test_given_existing_user_id_with_wallet_id_which_is_related_expected_output() {
        Wallet walletByUserIdAndWalletId = underTest.getWalletByUserIdAndWalletId(userWithWallet.getId(), walletOfUser.getId());
        assertEquals(walletOfUser.getId(), walletByUserIdAndWalletId.getId());
    }


}