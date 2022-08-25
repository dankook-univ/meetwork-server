package com.github.dankook_univ.meetwork.post.comment.infra.http.response;

import com.github.dankook_univ.meetwork.post.comment.domain.Comment;
import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@Component
public class CommentResponse {

    @NotBlank
    String id;

    @NotNull
    ProfileResponse writer;

    @NotBlank
    String content;

    @NotNull
    LocalDateTime createdAt;

    @NotNull
    LocalDateTime updatedAt;

    @Builder
    public CommentResponse(
        Comment comment
    ) {
        this.id = comment.getId().toString();
        this.writer = comment.getWriter().toResponse();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
