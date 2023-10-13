package com.example.rentservice.exception.auth;

public class PasswordResetTokenNotFoundException extends Exception {
    public PasswordResetTokenNotFoundException(String message) {
        super(message);
    }
}
