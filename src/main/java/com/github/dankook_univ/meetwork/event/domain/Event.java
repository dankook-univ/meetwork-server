package com.github.dankook_univ.meetwork.event.domain;

import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.event.infra.http.response.EventResponse;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends Core {

    @Column(nullable = false)
    private String name;

    @ManyToOne(targetEntity = Profile.class, fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "organizer_id")
    private Profile organizer;

    @Column(unique = true, nullable = false)
    private String code;

    @Column
    private String meetingUrl;

    @Builder
    public Event(String name, Profile organizer, String code, String meetingUrl) {
        Assert.hasText(name, "name must not be empty");
        Assert.hasText(code, "code must not be empty");

        this.name = name;
        this.organizer = organizer;
        this.code = code;
        this.meetingUrl = meetingUrl;
    }

    public EventResponse toResponse() {
        return EventResponse.builder()
            .event(this)
            .build();
    }

    public Event update(String name, String code, String meetingUrl) {
        if (name != null) {
            this.name = name.trim();
        }
        if (code != null) {
            this.code = code.trim();
        }
        if (meetingUrl != null) {
            this.meetingUrl = meetingUrl;
        }

        return this;
    }

    public Event setOrganizer(Profile organizer) {
        this.organizer = organizer;

        return this;
    }
}
