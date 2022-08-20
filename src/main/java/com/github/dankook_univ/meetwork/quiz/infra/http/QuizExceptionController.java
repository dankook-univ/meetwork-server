package com.github.dankook_univ.meetwork.quiz.infra.http;

import com.github.dankook_univ.meetwork.quiz.exceptions.AlreadyParticipantedQuizException;
import com.github.dankook_univ.meetwork.quiz.exceptions.ExistingQuizNameException;
import com.github.dankook_univ.meetwork.quiz.exceptions.NotFoundQuestionException;
import com.github.dankook_univ.meetwork.quiz.exceptions.NotFoundQuizException;
import com.github.dankook_univ.meetwork.quiz.exceptions.NotFoundQuizParticipantsException;
import com.github.dankook_univ.meetwork.quiz.exceptions.NotFoundQuizPermissionException;
import com.github.dankook_univ.meetwork.quiz.exceptions.NotParticipantQuizException;
import com.github.dankook_univ.meetwork.quiz.exceptions.QuestionAndQuizRelationshipException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QuizExceptionController {

    @ExceptionHandler(value = NotFoundQuizException.class)
    public ResponseEntity<String> notFoundQuizException(NotFoundQuizException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotFoundQuizPermissionException.class)
    public ResponseEntity<String> notFoundQuizPermissionException(
        NotFoundQuizPermissionException exception) {
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
            .body(exception.getMessage());
    }

    @ExceptionHandler(value = NotFoundQuestionException.class)
    public ResponseEntity<String> notFoundQuestionException(NotFoundQuestionException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(exception.getMessage());
    }

    @ExceptionHandler(value = NotFoundQuizParticipantsException.class)
    public ResponseEntity<String> notFoundQuizParticipantsException(
        NotFoundQuizParticipantsException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(exception.getMessage());
    }

    @ExceptionHandler(value = AlreadyParticipantedQuizException.class)
    public ResponseEntity<String> AlreadyParticipantedQuizException(
        AlreadyParticipantedQuizException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(exception.getMessage());
    }

    @ExceptionHandler(value = NotParticipantQuizException.class)
    public ResponseEntity<String> notParticipantQuizException(
        NotParticipantQuizException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(exception.getMessage());
    }

    @ExceptionHandler(value = QuestionAndQuizRelationshipException.class)
    public ResponseEntity<String> questionAndQuizRelationshipException(
        QuestionAndQuizRelationshipException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(exception.getMessage());
    }

    @ExceptionHandler(value = ExistingQuizNameException.class)
    public ResponseEntity<String> existingQuizNameException(ExistingQuizNameException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(exception.getMessage());
    }
}
