package com.example.sweezcustoms.exceptions;

public class CarDeclarationNotFoundException extends BaseException {
    public CarDeclarationNotFoundException(String message) {
        super(ErrorBody.builder().code(404).message(message).build());
    }
}
