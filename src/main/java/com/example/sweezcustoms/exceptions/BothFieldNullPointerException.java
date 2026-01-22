package com.example.sweezcustoms.exceptions;


import java.util.Date;

public class BothFieldNullPointerException extends BaseException {
    public BothFieldNullPointerException(String message) {
        super(ErrorBody.builder().message(message).code(400).date(new Date()).build());
    }
}
