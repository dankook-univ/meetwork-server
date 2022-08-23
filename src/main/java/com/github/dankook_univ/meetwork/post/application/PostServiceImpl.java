package com.github.dankook_univ.meetwork.post.application;

import com.github.dankook_univ.meetwork.board.application.BoardServiceImpl;
import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.board.exceptions.NotFoundBoardPermissionException;
import com.github.dankook_univ.meetwork.event.exceptions.NotFoundEventPermissionException;
import com.github.dankook_univ.meetwork.post.domain.Post;
import com.github.dankook_univ.meetwork.post.exceptions.NotFoundPostException;
import com.github.dankook_univ.meetwork.post.exceptions.NotFoundPostPermissionException;
import com.github.dankook_univ.meetwork.post.infra.http.request.PostCreateRequest;
import com.github.dankook_univ.meetwork.post.infra.http.request.PostUpdateRequest;
import com.github.dankook_univ.meetwork.post.infra.persistence.PostRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepositoryImpl postRepository;
    private final ProfileServiceImpl profileService;
    private final BoardServiceImpl boardService;

    @Override
    @Transactional
    public Post create(String memberId, PostCreateRequest request) {
        Board board = boardService.get(request.getBoardId());
        Profile profile = profileService.get(memberId, board.getEvent().getId().toString());
        if (board.getAdminOnly() && !profile.getIsAdmin()) {
            throw new NotFoundBoardPermissionException();
        }

        return postRepository.save(
            Post.builder()
                .content(request.getContent())
                .writer(profile)
                .board(board)
                .build()
        );
    }

    @Override
    @Transactional
    public Post update(String memberId, String postId, PostUpdateRequest request) {
        Post post = postRepository.getById(postId).orElseThrow(NotFoundPostException::new);
        Board board = boardService.get(post.getBoard().getId().toString());
        Profile profile = profileService.get(memberId, board.getEvent().getId().toString());

        if (board.getAdminOnly() && !profile.getIsAdmin()) {
            throw new NotFoundBoardPermissionException();
        }
        if (!Objects.equals(profile.getId().toString(), post.getWriter().getId().toString())) {
            throw new NotFoundPostPermissionException();
        }

        return post.update(request.getContent());
    }

    @Override
    public Post get(String memberId, String postId) {
        Post post = postRepository.getById(postId)
            .orElseThrow(NotFoundPostException::new);

        Boolean isEventMember = profileService.isEventMember(
            memberId,
            post.getBoard().getEvent().getId().toString()
        );
        if (!isEventMember) {
            throw new NotFoundEventPermissionException();
        }

        return post;
    }

    @Override
    public List<Post> getList(String memberId, String boardId, int page) {
        Board board = boardService.get(boardId);

        Boolean isEventMember = profileService.isEventMember(
            memberId,
            board.getEvent().getId().toString()
        );
        if (!isEventMember) {
            throw new NotFoundEventPermissionException();
        }

        return postRepository.getByBoardId(boardId, PageRequest.of(page - 1, 15));
    }

    @Override
    @Transactional
    public void delete(String memberId, String postId) {
        Post post = postRepository.getById(postId).orElseThrow(NotFoundPostException::new);
        Profile profile = profileService.get(
            memberId,
            post.getBoard().getEvent().getId().toString()
        );

        if (post.getWriter() == profile || profile.getIsAdmin()) {
            postRepository.delete(post);
        } else {
            throw new NotFoundPostPermissionException();
        }
    }
}
