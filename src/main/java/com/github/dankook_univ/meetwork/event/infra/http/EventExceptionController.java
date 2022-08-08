package com.github.dankook_univ.meetwork.event.infra.http;

import com.github.dankook_univ.meetwork.event.exceptions.ExistingCodeException;
import com.github.dankook_univ.meetwork.event.exceptions.NotFoundEventException;
import com.github.dankook_univ.meetwork.event.exceptions.NotFoundEventPermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EventExceptionController {

    @ExceptionHandler(value = ExistingCodeException.class)
    public ResponseEntity<String> existingCodeException(ExistingCodeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotFoundEventException.class)
    public ResponseEntity<String> notFoundEventException(NotFoundEventException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotFoundEventPermissionException.class)
    public ResponseEntity<String> notFoundEventPermissionException(
        NotFoundEventPermissionException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
