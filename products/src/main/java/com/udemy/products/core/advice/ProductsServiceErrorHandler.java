package com.udemy.products.core.advice;


import com.udemy.products.core.data.validation.ErrorMessage;
import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;


@ControllerAdvice
public class ProductsServiceErrorHandler {
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalStateException(IllegalArgumentException e, WebRequest request) {
        ErrorMessage message =new ErrorMessage(new Date(), e.getMessage());
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleUnregisteredExceptions(Exception e, WebRequest request) {
        ErrorMessage message =new ErrorMessage(new Date(), e.getMessage());
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {CommandExecutionException.class})
    public ResponseEntity<Object> handleCommandExecutionExceptions(CommandExecutionException e, WebRequest request) {
        ErrorMessage message =new ErrorMessage(new Date(), e.getMessage());
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
