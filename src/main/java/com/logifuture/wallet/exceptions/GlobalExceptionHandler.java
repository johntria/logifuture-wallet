package com.logifuture.wallet.exceptions;

import com.logifuture.wallet.dto.error.ErrorDTO;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.UUID;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleValidationErrors(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        String errorMessagesSeperatedWithComas = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(" , "));
        return ErrorDTO.builder().code(HttpStatus.BAD_REQUEST.getReasonPhrase()).message(errorMessagesSeperatedWithComas).build();
    }

    @ResponseBody
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadSchemaJson(HttpMessageNotReadableException ex) {
        log.error(ex.getMessage(), ex);
        return ErrorDTO.builder().code(HttpStatus.BAD_REQUEST.getReasonPhrase()).message("JSON schema is not correct, see api definition").build();
    }

    @ResponseBody
    @ExceptionHandler(value = {NoResourceFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFoundWithCustomMessage(NoResourceFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ErrorDTO.builder().code(HttpStatus.NOT_FOUND.getReasonPhrase()).message("You are trying to reach an endpoint which is not available, see api definition").build();
    }
    @ResponseBody
    @ExceptionHandler(value = {ServletException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadMethodForEndpoint(ServletException ex) {
        log.error(ex.getMessage(), ex);
        return ErrorDTO.builder().code(HttpStatus.BAD_REQUEST.getReasonPhrase()).message("The given request is not supported , see api definition").build();
    }

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ErrorDTO.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).message("Something gone wrong, contact support by giving this id" + UUID.randomUUID()).build();
    }

}