package com.example.sweezcustoms.exceptions;

public class NullEmployeesInCompany extends BaseException {
    public NullEmployeesInCompany(String message) {
        super(ErrorBody.builder().code(400).message(message).build());
    }
}
