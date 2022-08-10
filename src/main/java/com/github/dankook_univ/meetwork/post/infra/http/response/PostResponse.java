package com.github.dankook_univ.meetwork.post.infra.http.response;

import com.github.dankook_univ.meetwork.board.infra.http.response.BoardResponse;
import com.github.dankook_univ.meetwork.member.infra.http.response.MemberResponse;
import com.github.dankook_univ.meetwork.post.domain.Post;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
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
    UUID id;

    @NotNull
    MemberResponse member;

    @NotNull
    @NotEmpty
    String title;

    @NotNull
    @NotEmpty
    String content;

    @NotNull
    Profile writer;

    @NotNull
    BoardResponse board;

    List<String> fileUrls;

    @NotNull
    LocalDateTime createAt;

    @NotNull
    LocalDateTime updateAt;


    @Builder
    public PostResponse(
        Post post
    ) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter();
        this.board = post.getBoard().toResponse();
        this.createAt = post.getCreatedAt();
        this.updateAt = post.getUpdatedAt();
    }
}
