package com.github.dankook_univ.meetwork.post.infra.http;

import com.github.dankook_univ.meetwork.post.exceptions.NotFoundPostException;
import com.github.dankook_univ.meetwork.post.exceptions.NotFoundPostPermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostExceptionController {

    @ExceptionHandler(value = NotFoundPostException.class)
    public ResponseEntity<String> notFoundPostException(NotFoundPostException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotFoundPostPermissionException.class)
    public ResponseEntity<String> notFoundPostPermissionException(
        NotFoundPostPermissionException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(exception.getMessage());
    }
}
