package com.github.dankook_univ.meetwork.quiz.exceptions;

public class NotFoundQuizException extends RuntimeException {

    public NotFoundQuizException() {
        super("퀴즈가 존재하지 않습니다.");
    }
}
