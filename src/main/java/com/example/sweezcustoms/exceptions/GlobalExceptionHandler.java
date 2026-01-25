package com.example.sweezcustoms.exceptions;


import com.example.sweezcustoms.utils.InternalizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final InternalizationHelper internalizationHelper;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorBody> catchBaseException(BaseException baseException){

        ErrorBody errorBody = baseException.getErrorBody();
        errorBody.setMessage(internalizationHelper.getTranslation(errorBody.getMessage()));

        return ResponseEntity.status(HttpStatusCode.valueOf(errorBody.getCode())).body(errorBody);
    }
}
