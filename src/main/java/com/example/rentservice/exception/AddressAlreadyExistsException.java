package com.example.rentservice.exception;

public class AddressAlreadyExistsException extends Exception {
    public AddressAlreadyExistsException(String message) {
        super(message);
    }
}
