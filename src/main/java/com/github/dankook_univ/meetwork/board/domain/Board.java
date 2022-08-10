package com.github.dankook_univ.meetwork.board.domain;

import com.github.dankook_univ.meetwork.board.infra.http.response.BoardResponse;
import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.event.domain.Event;
import io.jsonwebtoken.lang.Assert;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends Core {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean adminOnly;

    @Builder
    public Board(Event event, String name, Boolean adminOnly) {
        Assert.notNull(event, "event must not be null");
        Assert.hasText(name, "name must not be empty");
        Assert.notNull(adminOnly, "adminOnly must not be null");

        this.event = event;
        this.name = name;
        this.adminOnly = adminOnly;
    }

    public BoardResponse toResponse() {
        return BoardResponse.builder()
            .id(this.getId())
            .createdAt(this.getCreatedAt())
            .updatedAt(this.getUpdatedAt())
            .event(this.getEvent().toResponse())
            .name(this.name)
            .adminOnly(this.adminOnly)
            .build();
    }

    public Board update(String name, Boolean adminOnly) {
        if (name != null) {
            this.name = name.trim();
        }
        if (adminOnly != null) {
            this.adminOnly = adminOnly;
        }
        return this;
    }
}
