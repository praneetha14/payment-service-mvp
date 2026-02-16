package com.payment.service.controller;

import com.payment.service.exception.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Global exception handler for the Finance Tracker application.
 * This class centralizes the handling of common exceptions thrown across controllers,
 * ensuring consistent and meaningful HTTP responses for API clients.
 * It maps specific exception types to appropriate HTTP status codes and response messages.
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
