package com.github.dankook_univ.meetwork.file.exceptions;

public class NotExistFileException extends RuntimeException {

    public NotExistFileException() {
        super("파일이 존재하지 않습니다.");
    }
}
