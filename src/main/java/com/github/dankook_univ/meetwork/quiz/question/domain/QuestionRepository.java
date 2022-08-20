package com.github.dankook_univ.meetwork.quiz.question.domain;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

    Question save(Question question);

    Optional<Question> getById(String id);

    List<Question> getByQuizId(String quizId);

    void delete(String quizId);
}
