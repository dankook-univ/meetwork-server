package com.github.dankook_univ.meetwork.quiz.exceptions;

public class QuestionAndQuizRelationshipException extends RuntimeException {

    public QuestionAndQuizRelationshipException() {
        super("질문과 퀴즈의 관계가 올바르지 않습니다.");
    }
}
