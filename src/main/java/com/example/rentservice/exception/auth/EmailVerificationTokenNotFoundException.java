package com.example.rentservice.exception.auth;

public class EmailVerificationTokenNotFoundException extends Exception {
    public EmailVerificationTokenNotFoundException(String message) {
        super(message);
    }
}
