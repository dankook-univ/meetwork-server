package com.github.dankook_univ.meetwork.quiz.quiz_participants.domain;

import java.util.List;
import java.util.Optional;

public interface QuizParticipantsRepository {

    QuizParticipants save(QuizParticipants quizParticipants);

    Optional<QuizParticipants> getByProfileIdAndQuizId(String quizId, String profileId);

    List<QuizParticipants> getByQuizId(String quizId);

    void delete(String quizId);

    void deleteByProfileId(String profileId);
}
