package com.github.dankook_univ.meetwork.post.comment.infra.http.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CommentCreateRequest {

    @NotNull
    @NotEmpty
    String content;

    @NotNull
    @NotEmpty
    String postId;
}
