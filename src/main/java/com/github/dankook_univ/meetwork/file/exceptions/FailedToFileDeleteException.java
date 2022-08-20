package com.github.dankook_univ.meetwork.file.exceptions;

public class FailedToFileDeleteException extends RuntimeException {

    public FailedToFileDeleteException() {
        super("파일 삭제에 실패했습니다.");
    }
}
