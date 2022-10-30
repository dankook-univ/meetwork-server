package com.github.dankook_univ.meetwork.quiz.quiz_participants.infra.persistence;

import com.github.dankook_univ.meetwork.quiz.quiz_participants.domain.QuizParticipants;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizParticipantsJpaRepository extends JpaRepository<QuizParticipants, Long> {

    Optional<QuizParticipants> findByProfileIdAndQuizId(Long profileId, Long quizId);

    List<QuizParticipants> findByQuizIdOrderByCountDesc(Long quizId);

    void deleteAllByQuizId(Long quizId);

    void deleteAllByProfileId(Long profileId);
}
