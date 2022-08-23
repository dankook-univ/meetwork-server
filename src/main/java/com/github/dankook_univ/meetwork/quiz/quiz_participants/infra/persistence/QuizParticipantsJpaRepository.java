package com.github.dankook_univ.meetwork.quiz.quiz_participants.infra.persistence;

import com.github.dankook_univ.meetwork.quiz.quiz_participants.domain.QuizParticipants;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizParticipantsJpaRepository extends JpaRepository<QuizParticipants, UUID> {

    Optional<QuizParticipants> getByProfileId(UUID profileId);

    Optional<QuizParticipants> findByProfileIdAndQuizId(UUID profileId, UUID quizId);

    List<QuizParticipants> findByQuizIdOrderByCountDesc(UUID quizId);

    void deleteAllByQuizId(UUID quizId);
}
