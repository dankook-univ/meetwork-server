package com.github.dankook_univ.meetwork.quiz.infra.persistence;

import com.github.dankook_univ.meetwork.quiz.domain.QQuiz;
import com.github.dankook_univ.meetwork.quiz.domain.Quiz;
import com.github.dankook_univ.meetwork.quiz.domain.QuizRepository;
import com.github.dankook_univ.meetwork.quiz.infra.http.response.QuizResponse;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.domain.QQuizParticipants;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryImpl implements QuizRepository {

    private final QuizJpaRepository quizRepository;

    private final JPAQueryFactory queryFactory;
    private final QQuiz quiz = QQuiz.quiz;
    private final QQuizParticipants quizParticipants = QQuizParticipants.quizParticipants;

    @Override
    @Transactional
    public List<QuizResponse> getQuizListWithJoin(String profileId, String eventId) {
        return queryFactory
            .select(
                Projections.constructor(QuizResponse.class,
                    quiz.id,
                    quiz.name,
                    quiz.createdAt,
                    quiz.updatedAt,
                    quizParticipants.isFinished
                ))
            .from(quizParticipants)
            .where(quiz.event.id.eq(UUID.fromString(eventId)))
            .rightJoin(quizParticipants.quiz, quiz)
            .on(quizParticipants.profile.id.eq(UUID.fromString(profileId)))
            .fetch();
    }

    @Override
    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public Optional<Quiz> getById(String quizId) {
        return quizRepository.findById(UUID.fromString(quizId));
    }

    @Override
    public Optional<Quiz> getByName(String name) {
        return quizRepository.getByName(name);
    }

    @Override
    public void delete(Quiz quiz) {
        quizRepository.delete(quiz);
    }

    @Override
    public void deleteByEventId(String eventId) {
        quizRepository.deleteAllByEventId(UUID.fromString(eventId));
    }
}
