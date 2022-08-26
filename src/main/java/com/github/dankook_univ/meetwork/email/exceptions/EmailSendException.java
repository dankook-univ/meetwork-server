package com.github.dankook_univ.meetwork.email.exceptions;

public class EmailSendException extends RuntimeException {

    public EmailSendException() {
        super("이메일 전송에 실패했습니다.");
    }
}
