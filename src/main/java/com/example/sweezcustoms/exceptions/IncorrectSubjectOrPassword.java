package com.example.sweezcustoms.exceptions;

public class IncorrectSubjectOrPassword extends BaseException {
    public IncorrectSubjectOrPassword(String message) {
        super(ErrorBody.builder().message(message).code(400).build());
    }
}
