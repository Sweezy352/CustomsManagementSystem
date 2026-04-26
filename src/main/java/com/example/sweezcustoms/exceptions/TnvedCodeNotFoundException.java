package com.example.sweezcustoms.exceptions;

public class TnvedCodeNotFoundException extends BaseException {
    public TnvedCodeNotFoundException(String message) {
        super(ErrorBody.builder().message(message).code(404).build());
    }
}
