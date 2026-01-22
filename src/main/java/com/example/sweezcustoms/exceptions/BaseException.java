package com.example.sweezcustoms.exceptions;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final ErrorBody errorBody;


    public BaseException(ErrorBody errorBody) {
        super(errorBody.getMessage());
        this.errorBody = errorBody;
    }
}
