package com.github.dankook_univ.meetwork.invitation.infra.http;

import com.github.dankook_univ.meetwork.invitation.exception.AlreadyEventJoinException;
import com.github.dankook_univ.meetwork.invitation.exception.NotFoundInvitationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvitationExceptionController {

    @ExceptionHandler(value = NotFoundInvitationException.class)
    public ResponseEntity<String> notFoundInvitationException(
        NotFoundInvitationException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = AlreadyEventJoinException.class)
    public ResponseEntity<String> alreadyEventJoinException(AlreadyEventJoinException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
