package com.github.dankook_univ.meetwork.quiz.question.infra.persistence;

import com.github.dankook_univ.meetwork.quiz.question.domain.Question;
import com.github.dankook_univ.meetwork.quiz.question.domain.QuestionRepository;
import java.util.List;
import java.util.Optional;
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
    public Optional<Question> getById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> getByQuizId(Long quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    @Override
    public Long countByQuizId(Long quizId) {
        return questionRepository.countByQuizId(quizId);
    }

    @Override
    public void delete(Long quizId) {
        questionRepository.deleteAllByQuizId(quizId);
    }
}
