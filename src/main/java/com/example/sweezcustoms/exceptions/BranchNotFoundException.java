package com.example.sweezcustoms.exceptions;

public class BranchNotFoundException extends BaseException {
    public BranchNotFoundException(String message) {
        super(ErrorBody.builder().code(404).message(message).build());
    }
}
