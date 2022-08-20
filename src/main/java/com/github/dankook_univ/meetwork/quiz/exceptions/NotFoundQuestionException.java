package com.github.dankook_univ.meetwork.quiz.exceptions;

public class NotFoundQuestionException extends RuntimeException {

    public NotFoundQuestionException() {
        super("질문이 존재하지 않습니다.");
    }
}
