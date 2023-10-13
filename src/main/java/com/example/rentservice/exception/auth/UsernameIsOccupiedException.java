package com.example.rentservice.exception.auth;

public class UsernameIsOccupiedException extends Exception {
    public UsernameIsOccupiedException(String message) {
        super(message);
    }
}
