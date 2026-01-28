package com.example.sweezcustoms.exceptions;

public class CodeConfirmationException extends BaseException {
    public CodeConfirmationException(String message) {
        super(ErrorBody.builder().code(400).message(message).build());
    }
}
