package com.github.dankook_univ.meetwork.quiz.quiz_participants.infra.persistence;

import com.github.dankook_univ.meetwork.quiz.quiz_participants.domain.QuizParticipants;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.domain.QuizParticipantsRepository;
import java.util.List;
import java.util.Optional;
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
    public Optional<QuizParticipants> getByProfileIdAndQuizId(Long profileId, Long quizId) {
        return quizParticipantsRepository.findByProfileIdAndQuizId(
            profileId,
            quizId
        );
    }

    @Override
    public List<QuizParticipants> getByQuizId(Long quizId) {
        return quizParticipantsRepository.findByQuizIdOrderByCountDesc(quizId);
    }

    @Override
    public void delete(Long quizId) {
        quizParticipantsRepository.deleteAllByQuizId(quizId);
    }

    @Override
    public void deleteByProfileId(Long profileId) {
        quizParticipantsRepository.deleteAllByProfileId(profileId);
    }
}
