package com.github.dankook_univ.meetwork.post.domain;

import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.post.infra.http.response.PostResponse;
import com.github.dankook_univ.meetwork.post.post_file.domain.PostFile;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.util.List;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
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

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String title;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Profile writer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Transient
    private List<PostFile> postFile;

    @Builder
    public Post(String title, String content, Profile writer, Board board) {
        Assert.hasText(title, "title must not be empty");
        Assert.hasText(content, "content must not be empty");
        Assert.notNull(writer, "writer must not be null");
        Assert.notNull(board, "board must not be null");

        this.title = title;
        this.content = content;
        this.board = board;
        this.writer = writer;
    }

    public PostResponse toResponse() {
        return PostResponse.builder()
            .post(this)
            .build();
    }

    public Post update(@Nullable String title, @Nullable String content) {
        if (title != null) {
            this.title = title.trim();
        }
        if (content != null) {
            this.content = content.trim();
        }
        return this;
    }

    public void addFile(PostFile postFile) {
        this.postFile.add(postFile);
    }
}
