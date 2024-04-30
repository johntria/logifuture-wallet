package com.logifuture.wallet.exceptions.wallet;

import com.logifuture.wallet.dto.error.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WalletGlobalExceptionHandler  {

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(value = {WalletAlreadyExists.class})
    public ErrorDTO handleException(WalletAlreadyExists walletAlreadyExists) {
        log.error(walletAlreadyExists.getMessage(), walletAlreadyExists);
        return ErrorDTO
                .builder()
                .code(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .message(walletAlreadyExists.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {WalletIsNotAssignedToUser.class})
    public ErrorDTO handleException(WalletIsNotAssignedToUser walletIsNotAssignedToUserExc) {
        log.error(walletIsNotAssignedToUserExc.getMessage(), walletIsNotAssignedToUserExc);
        return ErrorDTO
                .builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(walletIsNotAssignedToUserExc.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = {UnauthorizedUserForWallet.class})
    public ErrorDTO handleWalletIsNotAssignedToThisUser(UnauthorizedUserForWallet unauthorizedUserForWallet) {
        log.error(unauthorizedUserForWallet.getMessage(), unauthorizedUserForWallet);
        return ErrorDTO
                .builder()
                .code(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(unauthorizedUserForWallet.getMessage())
                .build();
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(value = {WalletStateException.class})
    public ErrorDTO handleWalletIsNotAssignedToThisUser(WalletStateException walletStateException) {
        log.error(walletStateException.getMessage(), walletStateException);
        return ErrorDTO
                .builder()
                .code(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .message(walletStateException.getMessage())
                .build();
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {WalletBalanceException.class})
    public ErrorDTO handleWalletIsNotAssignedToThisUser(WalletBalanceException walletBalanceException) {
        log.error(walletBalanceException.getMessage(), walletBalanceException);
        return ErrorDTO
                .builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(walletBalanceException.getMessage())
                .build();
    }

}