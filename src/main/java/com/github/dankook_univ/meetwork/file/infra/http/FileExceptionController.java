package com.github.dankook_univ.meetwork.file.infra.http;

import com.github.dankook_univ.meetwork.file.exceptions.FileUploadFailedException;
import com.github.dankook_univ.meetwork.file.exceptions.MinioStatObjectException;
import com.github.dankook_univ.meetwork.file.exceptions.NotExistFileException;
import com.github.dankook_univ.meetwork.file.exceptions.NotSupportedFileFormatException;
import com.github.dankook_univ.meetwork.profile.exceptions.ExistingNicknameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FileExceptionController {

    @ExceptionHandler(value = FileUploadFailedException.class)
    public ResponseEntity<String> fileUploadFailedException(FileUploadFailedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = MinioStatObjectException.class)
    public ResponseEntity<String> minioStatObjectException(MinioStatObjectException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotExistFileException.class)
    public ResponseEntity<String> NotExistFileException(NotExistFileException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotSupportedFileFormatException.class)
    public ResponseEntity<String> notSupportedFileFormatException(NotSupportedFileFormatException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
