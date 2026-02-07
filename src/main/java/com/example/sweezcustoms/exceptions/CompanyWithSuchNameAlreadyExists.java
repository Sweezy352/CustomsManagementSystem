package com.example.sweezcustoms.exceptions;

public class CompanyWithSuchNameAlreadyExists extends BaseException {
    public CompanyWithSuchNameAlreadyExists(String message) {
        super(ErrorBody.builder().code(400).message(message).build());
    }
}
