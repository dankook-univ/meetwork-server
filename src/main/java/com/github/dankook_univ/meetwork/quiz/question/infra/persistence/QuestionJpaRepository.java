package com.github.dankook_univ.meetwork.quiz.question.infra.persistence;

import com.github.dankook_univ.meetwork.quiz.question.domain.Question;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJpaRepository extends JpaRepository<Question, UUID> {

    List<Question> findByQuizId(UUID quizId);

    void deleteAllByQuizId(UUID quizId);
}
