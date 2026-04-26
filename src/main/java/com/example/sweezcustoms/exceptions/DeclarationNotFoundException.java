package com.example.sweezcustoms.exceptions;


public class DeclarationNotFoundException extends BaseException {
    public DeclarationNotFoundException(String message) {
        super(ErrorBody.builder().code(404).message(message).build());
    }
}
