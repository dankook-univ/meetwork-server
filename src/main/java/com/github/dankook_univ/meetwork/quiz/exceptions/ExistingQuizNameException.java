package com.github.dankook_univ.meetwork.quiz.exceptions;

public class ExistingQuizNameException extends RuntimeException {

    public ExistingQuizNameException() {
        super("이미 퀴즈 이름이 존재합니다.");
    }
}
