package com.example.sweezcustoms.exceptions;

public class AuthenticationException extends BaseException {
    public AuthenticationException(String message) {
        super(ErrorBody.builder().code(400).message(message).build());
    }
}
