package com.example.rentservice.exception.auth;

public class EmailIsOccupiedException extends Exception {
    public EmailIsOccupiedException(String message) {
        super(message);
    }
}
