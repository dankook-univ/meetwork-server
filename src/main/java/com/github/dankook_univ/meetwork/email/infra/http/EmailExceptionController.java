package com.github.dankook_univ.meetwork.email.infra.http;

import com.github.dankook_univ.meetwork.event.exceptions.ExistingCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmailExceptionController {

    @ExceptionHandler(value = ExistingCodeException.class)
    public ResponseEntity<String> existingCodeException(ExistingCodeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
