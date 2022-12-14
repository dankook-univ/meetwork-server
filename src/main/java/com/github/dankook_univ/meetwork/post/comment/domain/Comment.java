package com.github.dankook_univ.meetwork.post.comment.domain;

import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.post.comment.infra.http.response.CommentResponse;
import com.github.dankook_univ.meetwork.post.domain.Post;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Core {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Profile writer;

    @NotNull
    @ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @NotBlank
    private String content;

    @Builder
    public Comment(Profile writer, Post post, String content) {
        Assert.notNull(writer, "writer must not be null");
        Assert.notNull(post, "post must not be null");
        Assert.hasText(content, "content must not be null");

        this.writer = writer;
        this.post = post;
        this.content = content;
    }

    public Comment update(@Nullable String content) {
        if (content != null) {
            this.content = content.trim();
        }
        return this;
    }

    public CommentResponse toResponse() {
        return CommentResponse.builder()
            .comment(this)
            .build();
    }
}
