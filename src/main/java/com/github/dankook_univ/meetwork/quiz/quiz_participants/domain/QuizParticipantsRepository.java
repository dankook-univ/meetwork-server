package com.github.dankook_univ.meetwork.quiz.quiz_participants.domain;

import java.util.List;
import java.util.Optional;

public interface QuizParticipantsRepository {

    QuizParticipants save(QuizParticipants quizParticipants);

    Optional<QuizParticipants> getByProfileIdAndQuizId(Long quizId, Long profileId);

    List<QuizParticipants> getByQuizId(Long quizId);

    void delete(Long quizId);

    void deleteByProfileId(Long profileId);
}
