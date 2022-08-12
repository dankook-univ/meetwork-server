package com.github.dankook_univ.meetwork.post.application;

import com.github.dankook_univ.meetwork.board.application.BoardServiceImpl;
import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.board.exceptions.NotFoundBoardPermissionException;
import com.github.dankook_univ.meetwork.event.exceptions.NotFoundEventPermissionException;
import com.github.dankook_univ.meetwork.file.application.file.FileServiceImpl;
import com.github.dankook_univ.meetwork.file.domain.FileType;
import com.github.dankook_univ.meetwork.post.domain.Post;
import com.github.dankook_univ.meetwork.post.exceptions.NotFoundPostException;
import com.github.dankook_univ.meetwork.post.infra.http.request.PostCreateRequest;
import com.github.dankook_univ.meetwork.post.infra.persistence.PostRepositoryImpl;
import com.github.dankook_univ.meetwork.post.post_file.domain.PostFile;
import com.github.dankook_univ.meetwork.post.post_file.infra.persistence.PostFileRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.util.List;
import java.util.stream.Collectors;
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

        return injectFilesIntoPost(post);
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

        List<Post> list = postRepository.getByBoardId(boardId, PageRequest.of(page - 1, 15));
        return list.stream().map(this::injectFilesIntoPost).collect(Collectors.toList());
    }

    private Post injectFilesIntoPost(Post post) {
        List<PostFile> postFiles = postFileRepository.getByPostId(post.getId().toString());
        if (postFiles != null) {
            postFiles.forEach(post::addFile);
        }
        return post;
    }
}
