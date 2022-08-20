package com.github.dankook_univ.meetwork.quiz.quiz_participants.infra.persistence;

import com.github.dankook_univ.meetwork.quiz.quiz_participants.domain.QuizParticipants;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.domain.QuizParticipantsRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuizParticipantsRepositoryImpl implements QuizParticipantsRepository {

    private final QuizParticipantsJpaRepository quizParticipantsRepository;

    @Override
    public QuizParticipants save(QuizParticipants quizParticipants) {
        return quizParticipantsRepository.save(quizParticipants);
    }

    @Override
    public Optional<QuizParticipants> getByProfileIdAndQuizId(String profileId, String quizId) {
        return quizParticipantsRepository.findByProfileIdAndQuizId(
            UUID.fromString(profileId),
            UUID.fromString(quizId)
        );
    }

    @Override
    public List<QuizParticipants> getByQuizId(String quizId) {
        return quizParticipantsRepository.findByQuizIdOrderByCountDesc(UUID.fromString(quizId));
    }

    @Override
    public void delete(String quizId) {
        quizParticipantsRepository.deleteAllByQuizId(UUID.fromString(quizId));
    }
}
