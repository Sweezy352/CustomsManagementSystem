package com.example.sweezcustoms.exceptions;

public class PasswordDoesNotMatchException extends BaseException {
    public PasswordDoesNotMatchException(String message) {
        super(ErrorBody.builder().code(400).message(message).build());
    }
}
