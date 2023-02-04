package com.thrymr.hotel.management.exception;

public class IdNotFoundException extends RuntimeException {
    private String message;

    public IdNotFoundException(String message) {
        super(message);

    }
}

