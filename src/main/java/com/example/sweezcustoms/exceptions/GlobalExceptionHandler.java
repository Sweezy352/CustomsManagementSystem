package com.example.sweezcustoms.exceptions;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorBody> catchBaseException(BaseException baseException){
        return ResponseEntity.status(baseException.getErrorBody().getCode()).body(baseException.getErrorBody());
    }
}
