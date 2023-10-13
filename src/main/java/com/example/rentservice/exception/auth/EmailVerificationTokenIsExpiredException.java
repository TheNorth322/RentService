package com.example.rentservice.exception.auth;

public class EmailVerificationTokenIsExpiredException extends Exception {
    public EmailVerificationTokenIsExpiredException(String message) {
        super(message);
    }
}
