package com.github.dankook_univ.meetwork.post.domain;

import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.post.comment.domain.Comment;
import com.github.dankook_univ.meetwork.post.infra.http.response.PostResponse;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
public class Post extends Core {

    @OneToMany(targetEntity = Comment.class, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("updatedAt asc")
    private final List<Comment> comments = new ArrayList<>();

    @NotBlank
    @Column(length = 32768, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Profile writer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Post(String content, Profile writer, Board board) {
        Assert.hasText(content, "content must not be empty");
        Assert.notNull(writer, "writer must not be null");
        Assert.notNull(board, "board must not be null");

        this.content = content;
        this.board = board;
        this.writer = writer;
    }

    public PostResponse toResponse() {
        return PostResponse.builder()
            .post(this)
            .build();
    }

    public Post update(@Nullable String content) {
        if (content != null) {
            this.content = content.trim();
        }
        return this;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void deleteComment(Comment comment) {
        comments.remove(comment);
    }
}
