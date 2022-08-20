package com.github.dankook_univ.meetwork.quiz.question.infra.persistence;

import com.github.dankook_univ.meetwork.quiz.question.domain.Question;
import com.github.dankook_univ.meetwork.quiz.question.domain.QuestionRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {

    private final QuestionJpaRepository questionRepository;

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Optional<Question> getById(String id) {
        return questionRepository.findById(UUID.fromString(id));
    }

    @Override
    public List<Question> getByQuizId(String quizId) {
        return questionRepository.findByQuizId(UUID.fromString(quizId));
    }

    @Override
    public void delete(String quizId) {
        questionRepository.deleteAllByQuizId(UUID.fromString(quizId));
    }
}
