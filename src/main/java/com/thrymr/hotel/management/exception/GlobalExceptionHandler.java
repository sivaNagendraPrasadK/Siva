package com.thrymr.hotel.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<String> handleNoSuchElementException(IdNotFoundException idNotFoundException){
        return new ResponseEntity<String>(idNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNoSuchElementException(NullPointerException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
