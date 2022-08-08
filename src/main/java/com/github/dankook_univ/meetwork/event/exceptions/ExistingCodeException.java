package com.github.dankook_univ.meetwork.event.exceptions;

public class ExistingCodeException extends RuntimeException {

    public ExistingCodeException() {
        super("이미 존재하는 code입니다.");
    }
}
