package com.github.dankook_univ.meetwork.auth.infra.http;

import com.github.dankook_univ.meetwork.auth.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionController {

    @ExceptionHandler(value = ExistingAuthException.class)
    public ResponseEntity<String> existingAuthException(ExistingAuthException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = InvalidClientException.class)
    public ResponseEntity<String> invalidClientException(InvalidClientException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = InvalidKakaoTokenException.class)
    public ResponseEntity<String> invalidKakaoTokenException(InvalidKakaoTokenException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity<String> invalidTokenException(InvalidTokenException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotFoundAuthException.class)
    public ResponseEntity<String> notFoundAuthException(NotFoundAuthException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotFoundMemberException.class)
    public ResponseEntity<String> notFoundMemberException(NotFoundMemberException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotFoundRoleException.class)
    public ResponseEntity<String> notFoundRoleException(NotFoundRoleException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }
}
