package com.github.dankook_univ.meetwork.post.application;

import com.github.dankook_univ.meetwork.board.application.BoardServiceImpl;
import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.board.exceptions.NotFoundBoardPermissionException;
import com.github.dankook_univ.meetwork.common.service.SecurityUtilService;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final SecurityUtilService securityUtilService;
    private final PostRepositoryImpl postRepository;
    private final ProfileServiceImpl profileService;
    private final BoardServiceImpl boardService;

    @Override
    @Transactional
    public Post create(Long memberId, PostCreateRequest request) {
        Board board = boardService.get(request.getBoardId());
        Profile profile = profileService.get(memberId, board.getEvent().getId());
        if (board.getAdminOnly() && !profile.getIsAdmin()) {
            throw new NotFoundBoardPermissionException();
        }

        return postRepository.save(
            Post.builder()
                .content(securityUtilService.protectInputValue(request.getContent()))
                .writer(profile)
                .board(board)
                .build()
        );
    }

    @Override
    @Transactional
    public Post update(Long memberId, Long postId, PostUpdateRequest request) {
        Post post = postRepository.getById(postId).orElseThrow(NotFoundPostException::new);
        Board board = boardService.get(post.getBoard().getId());
        Profile profile = profileService.get(memberId, board.getEvent().getId());

        if (board.getAdminOnly() && !profile.getIsAdmin()) {
            throw new NotFoundBoardPermissionException();
        }
        if (!profile.getId().equals(post.getWriter().getId())) {
            throw new NotFoundPostPermissionException();
        }

        return post.update(securityUtilService.protectInputValue(request.getContent()));
    }

    @Override
    public Post get(Long memberId, Long postId) {
        Post post = postRepository.getById(postId).orElseThrow(NotFoundPostException::new);

        if (
            !profileService.isEventMember(memberId, post.getBoard().getEvent().getId())
        ) {
            throw new NotFoundEventPermissionException();
        }

        return post;
    }

    @Override
    public List<Post> getList(Long memberId, Long boardId, int page) {
        Board board = boardService.get(boardId);

        if (!profileService.isEventMember(memberId, board.getEvent().getId())) {
            throw new NotFoundEventPermissionException();
        }

        return postRepository.getByBoardId(boardId, PageRequest.of(page - 1, 15));
    }

    @Override
    @Transactional
    public void delete(Long memberId, Long postId) {
        Post post = postRepository.getById(postId).orElseThrow(NotFoundPostException::new);
        Profile profile = profileService.get(
            memberId,
            post.getBoard().getEvent().getId()
        );

        if (!(post.getWriter() == profile || profile.getIsAdmin())) {
            throw new NotFoundPostPermissionException();
        }

        postRepository.delete(post);
    }
}
