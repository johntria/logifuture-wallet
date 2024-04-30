package com.logifuture.wallet.exceptions.user;

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
public class UserGlobalExceptionHandler  {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ErrorDTO handleException(UserNotFoundException userNotFoundException) {
        log.error(userNotFoundException.getMessage(), userNotFoundException);
        return ErrorDTO
                .builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(userNotFoundException.getMessage())
                .build();
    }
}