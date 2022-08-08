package com.github.dankook_univ.meetwork.board.infra.http;

import com.github.dankook_univ.meetwork.board.exceptions.ExistingBoardNameException;
import com.github.dankook_univ.meetwork.board.exceptions.NotFoundBoardException;
import com.github.dankook_univ.meetwork.board.exceptions.NotFoundBoardPermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BoardExceptionController {

    @ExceptionHandler(value = ExistingBoardNameException.class)
    public ResponseEntity<String> existingBoardNameException(ExistingBoardNameException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotFoundBoardException.class)
    public ResponseEntity<String> notExistingBoardException(NotFoundBoardException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotFoundBoardPermissionException.class)
    public ResponseEntity<String> notFoundBoardPermissionException(
        NotFoundBoardPermissionException exception) {
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
            .body(exception.getMessage());
    }
}
