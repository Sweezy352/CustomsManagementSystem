package com.example.sweezcustoms.exceptions;

public class DeclarationProductNotFoundException extends BaseException {
    public DeclarationProductNotFoundException(String message) {
        super(ErrorBody.builder().code(404).message(message).build());
    }
}
