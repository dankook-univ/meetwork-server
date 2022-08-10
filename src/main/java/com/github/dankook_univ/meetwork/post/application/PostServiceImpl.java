package com.github.dankook_univ.meetwork.post.application;

import com.github.dankook_univ.meetwork.board.application.BoardServiceImpl;
import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.board.exceptions.NotFoundBoardPermissionException;
import com.github.dankook_univ.meetwork.file.application.file.FileServiceImpl;
import com.github.dankook_univ.meetwork.file.domain.FileType;
import com.github.dankook_univ.meetwork.post.domain.Post;
import com.github.dankook_univ.meetwork.post.exceptions.NotFoundPostException;
import com.github.dankook_univ.meetwork.post.exceptions.NotFoundPostPermissionException;
import com.github.dankook_univ.meetwork.post.infra.http.request.PostCreateRequest;
import com.github.dankook_univ.meetwork.post.infra.http.request.PostUpdateRequest;
import com.github.dankook_univ.meetwork.post.infra.persistence.PostRepositoryImpl;
import com.github.dankook_univ.meetwork.post.post_file.domain.PostFile;
import com.github.dankook_univ.meetwork.post.post_file.infra.persistence.PostFileRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
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
    private final FileServiceImpl fileService;
    private final PostFileRepositoryImpl postFileRepository;

    @Override
    @Transactional
    public Post create(String memberId, PostCreateRequest request) {
        Board board = boardService.get(request.getBoardId());
        Profile profile = profileService.get(memberId, board.getEvent().getId().toString());
        if (board.getAdminOnly() && !profile.getIsAdmin()) {
            throw new NotFoundBoardPermissionException();
        }

        Post post = postRepository.save(
            Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(profile)
                .board(board)
                .build()
        );
        if (request.getFiles() != null) {
            request.getFiles().forEach((file) ->
                post.addFile(
                    postFileRepository.save(
                        PostFile.builder()
                            .post(post)
                            .file(fileService.upload(profile, FileType.post, file))
                            .build()
                    ))
            );
        }
        return post;
    }

    @Override
    @Transactional
    public Post update(String memberId, String postId, PostUpdateRequest request) {
        //게시글 불러오기
        Post post = postRepository.getById(UUID.fromString(postId))
            .orElseThrow(NotFoundPostException::new);

        Board board = boardService.get(post.getBoard().getId().toString());
        // event 참여자 맞나 확인
        Profile profile = profileService.get(memberId, board.getEvent().getId().toString());

        if (board.getAdminOnly() && !profile.getIsAdmin()) {
            throw new NotFoundBoardPermissionException();
        }
        if (!Objects.equals(profile.getId().toString(), post.getWriter().getId().toString())) {
            throw new NotFoundPostPermissionException();
        }

        post.update(request.getTitle(), request.getContent());

        if (request.getFiles() != null) {
            List<PostFile> postFiles = postFileRepository.getByPostId(post.getId());
            for (PostFile postFile : postFiles) {
                fileService.delete(postFile.getFile().getId());
                postFileRepository.delete(postFile);
            }
            request.getFiles().forEach((file) ->
                post.addFile(
                    postFileRepository.save(
                        PostFile.builder()
                            .post(post)
                            .file(fileService.upload(profile, FileType.post, file))
                            .build()
                    ))
            );
        }
        return post;
    }

    @Override
    public Post get(String memberId, String postId) {
        Post post = postRepository.getById(UUID.fromString(postId))
            .orElseThrow(NotFoundPostException::new);

        Board board = boardService.get(post.getBoard().getId().toString());
        profileService.get(memberId, board.getEvent().getId().toString());
        List<PostFile> postFiles = postFileRepository.getByPostId(UUID.fromString(postId));
        if (postFiles != null) {
            postFiles.forEach(postFileRepository::delete);
        }
        return post;
    }

    @Override
    public List<Post> getList(String memberId, String boardId, int page) {
        Board board = boardService.get(boardId);
        profileService.get(memberId, board.getEvent().getId().toString());

        return postRepository.getByBoardId(UUID.fromString(boardId), PageRequest.of(page - 1, 15));
    }

    @Override
    @Transactional
    public void delete(String memberId, String postId) {
        Post post = postRepository.getById(UUID.fromString(postId))
            .orElseThrow(NotFoundPostException::new);
        Board board = boardService.get(post.getBoard().getId().toString());
        Profile profile = profileService.get(memberId, board.getEvent().getId().toString());

        if (post.getWriter() == profile || profile.getIsAdmin()) {
            List<PostFile> postFiles = postFileRepository.getByPostId(UUID.fromString(postId));
            if (postFiles != null) {
                postFiles.forEach(postFileRepository::delete);
            }
            postRepository.delete(post);
        }
    }
}
