package com.github.dankook_univ.meetwork.profile.infra.http;

import com.github.dankook_univ.meetwork.profile.exceptions.ExistingNicknameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProfileExceptionController {

    @ExceptionHandler(value = ExistingNicknameException.class)
    public ResponseEntity<String> existingNicknameException(ExistingNicknameException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
