package com.example.rentservice.exception;

public class UserRoomAlreadyExistsException extends Exception {
    public UserRoomAlreadyExistsException(String message) {
        super(message);
    }
}
