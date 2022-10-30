package com.github.dankook_univ.meetwork.quiz.question.infra.persistence;

import com.github.dankook_univ.meetwork.quiz.question.domain.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJpaRepository extends JpaRepository<Question, Long> {

    List<Question> findByQuizId(Long quizId);

    void deleteAllByQuizId(Long quizId);

    Long countByQuizId(Long quizId);
}
