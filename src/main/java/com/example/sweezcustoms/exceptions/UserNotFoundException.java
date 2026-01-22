package com.example.sweezcustoms.exceptions;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String message) {
        super(ErrorBody.builder().message(message).code(404).build());
    }
}
