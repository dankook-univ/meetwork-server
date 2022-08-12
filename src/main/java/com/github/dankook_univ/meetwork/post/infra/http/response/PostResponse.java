package com.github.dankook_univ.meetwork.post.infra.http.response;

import com.github.dankook_univ.meetwork.board.infra.http.response.BoardResponse;
import com.github.dankook_univ.meetwork.file.infra.http.response.FileResponse;
import com.github.dankook_univ.meetwork.post.domain.Post;
import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponse {

    @NotNull
    @NotEmpty
    String id;

    @NotNull
    @NotEmpty
    String title;

    @NotNull
    @NotEmpty
    String content;

    @NotNull
    ProfileResponse writer;

    @NotNull
    BoardResponse board;

    List<FileResponse> fileUrls;

    @NotNull
    LocalDateTime createAt;

    @NotNull
    LocalDateTime updateAt;


    @Builder
    public PostResponse(
        Post post
    ) {
        this.id = post.getId().toString();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter().toResponse();
        this.board = post.getBoard().toResponse();
        this.fileUrls = post.getPostFile().stream().map((postFile) ->
            postFile.getFile().toResponse()).collect(Collectors.toList());
        this.createAt = post.getCreatedAt();
        this.updateAt = post.getUpdatedAt();
    }
}
