package com.example.sweezcustoms.exceptions;


import com.example.sweezcustoms.utils.InternalizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final InternalizationHelper internalizationHelper;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorBody> catchBaseException(BaseException baseException){

        ErrorBody errorBody = baseException.getErrorBody();
        errorBody.setMessage(internalizationHelper.getTranslation(errorBody.getMessage()));
        errorBody.setDate(new Date());

        return ResponseEntity.status(HttpStatusCode.valueOf(errorBody.getCode())).body(errorBody);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorBody> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        ErrorBody errorBody = ErrorBody.builder().code(400).message(ex.getFieldError().getDefaultMessage()).date(new Date()).build();
        return ResponseEntity.badRequest().body(errorBody);
    }
}
