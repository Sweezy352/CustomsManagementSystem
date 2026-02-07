package com.example.sweezcustoms.exceptions;

public class CompanyNotFoundException extends BaseException {
    public CompanyNotFoundException(String message) {
        super(ErrorBody.builder().code(404).message(message).build());
    }
}
