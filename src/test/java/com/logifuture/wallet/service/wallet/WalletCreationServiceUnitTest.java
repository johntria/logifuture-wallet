package com.logifuture.wallet.service.wallet;

import com.logifuture.wallet.dto.wallet.request.CreateWalletRequest;
import com.logifuture.wallet.dto.wallet.response.CreateWalletResponse;
import com.logifuture.wallet.entitity.StateOfWallet;
import com.logifuture.wallet.entitity.User;
import com.logifuture.wallet.entitity.Wallet;
import com.logifuture.wallet.exceptions.user.UserNotFoundException;
import com.logifuture.wallet.exceptions.wallet.WalletAlreadyExists;
import com.logifuture.wallet.mapper.WalletMapper;
import com.logifuture.wallet.repository.WalletRepository;
import com.logifuture.wallet.service.user.UserService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

/**
 * Unit test for {@link WalletCreationService}
 */
@ExtendWith(MockitoExtension.class)
class WalletCreationServiceUnitTest {
    /**
     * Mocked {@link WalletRepository}
     */
    @Mock
    WalletRepository walletRepository;
    /**
     * Mocked {@link UserService}
     */
    @Mock
    UserService userService;
    /**
     * Mocked {@link WalletMapper}
     */
    @Mock
    WalletMapper walletMapper;
    /**
     * Under test {@link WalletCreationService}
     */
    @InjectMocks
    WalletCreationService walletCreationService;

    /**
     * Unit test for {@link WalletCreationService#createWallet(CreateWalletRequest)}
     * GIVEN: A not existing user
     * EXPECTED: {@link UserNotFoundException}
     */
    @Test
    public void createWallet_given_not_existing_userID_expected_exception() {
        Long userID = new Random().nextLong();
        String exceptionMessage = "User with id:" + userID + " not found";
        CreateWalletRequest given = new CreateWalletRequest(userID);
        when(userService.findUserById(any())).thenThrow(new UserNotFoundException(exceptionMessage));
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> walletCreationService.createWallet(given));
        assertEquals(exceptionMessage, userNotFoundException.getMessage());
    }

    /**
     * Unit test for {@link WalletCreationService#createWallet(CreateWalletRequest)}
     * GIVEN: An existing user with wallet
     * EXPECTED: {@link WalletAlreadyExists}
     */
    @Test
    public void createWallet_given_existing_userID_with_wallet_expected_exception() {
        Long userID = new Random().nextLong();
        String expectedExceptionMessage = "User already has a wallet";
        CreateWalletRequest given = new CreateWalletRequest(userID);
        when(userService.findUserById(any())).thenReturn(new User());
        when(walletRepository.findByUserId(any())).thenReturn(Optional.of(new Wallet()));
        WalletAlreadyExists walletAlreadyExists = assertThrows(WalletAlreadyExists.class, () -> walletCreationService.createWallet(given));
        assertEquals(expectedExceptionMessage, walletAlreadyExists.getMessage());
    }
    /**
     * Unit test for {@link WalletCreationService#createWallet(CreateWalletRequest)}
     * GIVEN: An existing user without wallet
     * EXPECTED: A created wallet
     */
    @Test
    public void createWallet_given_existing_userID_without_wallet_expected_to_be_created() {
        Long userID = new Random().nextLong();
        Long walletID = new Random().nextLong();

        User mockedUser = new User();
        mockedUser.setId(userID);
        mockedUser.setFirstName("test");
        mockedUser.setLastName("test");

        Wallet mockedWallet = new Wallet();
        mockedWallet.setUser(mockedUser);
        mockedWallet.setBalance(BigDecimal.ZERO);
        mockedWallet.setId(walletID);
        mockedWallet.setState(StateOfWallet.AVAILABLE_BALANCE);

        CreateWalletRequest createWalletRequest = new CreateWalletRequest(userID);
        CreateWalletResponse walletResponse = new CreateWalletResponse(mockedWallet.getId(), mockedWallet.getBalance(), mockedWallet.getState());

        when(userService.findUserById(any())).thenReturn(mockedUser);
        when(walletRepository.findByUserId(any())).thenReturn(Optional.empty());
        when(walletRepository.save(any())).thenReturn(mockedWallet);
        when(walletMapper.walletToCreateWalletResponse(any())).thenReturn(walletResponse);

        CreateWalletResponse wallet = walletCreationService.createWallet(createWalletRequest);

        verify(userService, times(1)).findUserById(any());
        verify(walletRepository, times(1)).findByUserId(any());
        verify(walletRepository, times(1)).save(any());
        verify(walletMapper, times(1)).walletToCreateWalletResponse(any());

        assertEquals(walletResponse, wallet);
    }
}