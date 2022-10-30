package com.github.dankook_univ.meetwork.post.comment.infra.persistence;

import com.github.dankook_univ.meetwork.post.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

}
