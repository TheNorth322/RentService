package com.example.rentservice.exception;

public class BankAlreadyExistsException extends Exception {
    public BankAlreadyExistsException(String message) {
        super(message);
    }
}
