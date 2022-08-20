package com.github.dankook_univ.meetwork.file.exceptions;

public class FailedToFileUploadException extends RuntimeException {

    public FailedToFileUploadException() {
        super("파일 업로드에 실패했습니다.");
    }
}
