package com.github.dankook_univ.meetwork.file.exceptions;

public class NotSupportedFileFormatException extends RuntimeException {

    public NotSupportedFileFormatException(String mime) {
        super(mime + " 은 지원하지 않는 파일 형식입니다.");
    }
}
