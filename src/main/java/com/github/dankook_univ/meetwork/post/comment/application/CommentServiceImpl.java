package com.github.dankook_univ.meetwork.post.comment.application;

import com.github.dankook_univ.meetwork.post.application.PostServiceImpl;
import com.github.dankook_univ.meetwork.post.comment.domain.Comment;
import com.github.dankook_univ.meetwork.post.comment.exceptions.NotFoundCommentException;
import com.github.dankook_univ.meetwork.post.comment.exceptions.NotFoundCommentPermissionException;
import com.github.dankook_univ.meetwork.post.comment.infra.http.request.CommentCreateRequest;
import com.github.dankook_univ.meetwork.post.comment.infra.http.request.CommentUpdateRequest;
import com.github.dankook_univ.meetwork.post.comment.infra.persistence.CommentRepositoryImpl;
import com.github.dankook_univ.meetwork.post.domain.Post;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryImpl commentRepository;

    private final ProfileServiceImpl profileService;

    private final PostServiceImpl postService;

    @Override
    @Transactional
    public Comment create(String memberId, CommentCreateRequest request) {
        Post post = postService.get(memberId, request.getPostId());
        Profile profile = profileService.get(
            memberId,
            post.getBoard().getEvent().getId().toString()
        );

        Comment comment = commentRepository.save(
            Comment.builder()
                .writer(profile)
                .content(request.getContent())
                .post(post)
                .build()
        );

        post.addComment(comment);

        return comment;
    }

    @Override
    @Transactional
    public Comment update(String memberId, String commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.getById(commentId)
            .orElseThrow(NotFoundCommentException::new);
        Profile profile = profileService.get(
            memberId,
            comment.getPost().getBoard().getEvent().getId().toString()
        );

        if (!Objects.equals(profile.getId().toString(), comment.getWriter().getId().toString())) {
            throw new NotFoundCommentPermissionException();
        }

        return comment.update(request.getContent());
    }

    @Override
    @Transactional
    public void delete(String memberId, String commentId) {
        Comment comment = commentRepository.getById(commentId)
            .orElseThrow(NotFoundCommentException::new);
        Profile profile = profileService.get(
            memberId,
            comment.getPost().getBoard().getEvent().getId().toString()
        );

        if (profile == comment.getWriter() || profile.getIsAdmin()) {
            commentRepository.delete(comment);
        } else {
            throw new NotFoundCommentPermissionException();
        }

    }
}
