package com.github.dankook_univ.meetwork.quiz.question.domain;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

    Question save(Question question);

    Optional<Question> getById(Long id);

    List<Question> getByQuizId(Long quizId);

    Long countByQuizId(Long quizId);

    void delete(Long quizId);
}
