package com.github.dankook_univ.meetwork.post.comment.infra.persistence;

import com.github.dankook_univ.meetwork.post.comment.domain.Comment;
import com.github.dankook_univ.meetwork.post.comment.domain.CommentRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> getById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
