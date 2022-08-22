package com.github.dankook_univ.meetwork.post.comment.infra.http;

import com.github.dankook_univ.meetwork.post.comment.exceptions.NotFoundCommentException;
import com.github.dankook_univ.meetwork.post.comment.exceptions.NotFoundCommentPermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommentExceptionController {

    @ExceptionHandler(value = NotFoundCommentException.class)
    public ResponseEntity<String> notFoundCommentException(NotFoundCommentException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotFoundCommentPermissionException.class)
    public ResponseEntity<String> notFoundCommentPermissionException(
        NotFoundCommentPermissionException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(exception.getMessage());
    }
}
