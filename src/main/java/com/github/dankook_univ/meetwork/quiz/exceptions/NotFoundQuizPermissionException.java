package com.github.dankook_univ.meetwork.quiz.exceptions;

public class NotFoundQuizPermissionException extends RuntimeException {

    public NotFoundQuizPermissionException() {
        super("퀴즈에 대한 권한이 없습니다.");
    }
}
