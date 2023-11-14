package com.example.rentservice.exception;

public class BankNotFoundException extends Exception {
    public BankNotFoundException(String message) {
        super(message);
    }
}
