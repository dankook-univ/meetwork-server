package com.github.dankook_univ.meetwork.quiz.infra.persistence;

import com.github.dankook_univ.meetwork.quiz.domain.Quiz;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizJpaRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> getByEventId(Long eventId);

    Optional<Quiz> getByName(String name);

    void deleteAllByEventId(Long eventId);
}
