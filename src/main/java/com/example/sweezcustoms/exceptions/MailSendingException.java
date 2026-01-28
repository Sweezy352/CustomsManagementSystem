package com.example.sweezcustoms.exceptions;

public class MailSendingException extends BaseException {
    public MailSendingException(String message) {
        super(ErrorBody.builder().code(400).message(message).build());
    }
}
