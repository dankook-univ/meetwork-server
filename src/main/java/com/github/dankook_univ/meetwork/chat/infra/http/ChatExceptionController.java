package com.github.dankook_univ.meetwork.chat.infra.http;

import com.github.dankook_univ.meetwork.chat.exceptions.AlreadyChatRoomNameException;
import com.github.dankook_univ.meetwork.chat.exceptions.NotFoundChatRoomException;
import com.github.dankook_univ.meetwork.chat.exceptions.NotFoundChatRoomPermissionException;
import com.github.dankook_univ.meetwork.chat.exceptions.NotParticipatedMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ChatExceptionController {

    @ExceptionHandler(value = AlreadyChatRoomNameException.class)
    public ResponseEntity<String> alreadyChatRoomNameException(
        AlreadyChatRoomNameException exception
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotFoundChatRoomException.class)
    public ResponseEntity<String> notFoundChatRoomException(NotFoundChatRoomException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotFoundChatRoomPermissionException.class)
    public ResponseEntity<String> notFoundChatRoomPermissionException(
        NotFoundChatRoomPermissionException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotParticipatedMemberException.class)
    public ResponseEntity<String> notParticipatedMemberException(
        NotParticipatedMemberException exception
    ) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }
}
