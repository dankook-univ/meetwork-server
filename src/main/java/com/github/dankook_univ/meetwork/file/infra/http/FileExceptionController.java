package com.github.dankook_univ.meetwork.file.infra.http;

import com.github.dankook_univ.meetwork.file.exceptions.FailedToFileDeleteException;
import com.github.dankook_univ.meetwork.file.exceptions.FailedToFileUploadException;
import com.github.dankook_univ.meetwork.file.exceptions.NotExistFileException;
import com.github.dankook_univ.meetwork.file.exceptions.NotSupportedFileFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FileExceptionController {

    @ExceptionHandler(value = FailedToFileDeleteException.class)
    public ResponseEntity<String> failedToFileDeleteException(
        FailedToFileDeleteException exception
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = FailedToFileUploadException.class)
    public ResponseEntity<String> fileUploadFailedException(FailedToFileUploadException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotExistFileException.class)
    public ResponseEntity<String> NotExistFileException(NotExistFileException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotSupportedFileFormatException.class)
    public ResponseEntity<String> notSupportedFileFormatException(
        NotSupportedFileFormatException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
