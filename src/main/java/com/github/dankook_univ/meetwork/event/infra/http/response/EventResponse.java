package com.github.dankook_univ.meetwork.event.infra.http.response;

import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventResponse {
    
    String id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String name;
    ProfileResponse organizer;
    String code;
    String meetingCode;

    @Builder
    public EventResponse(
        Event event
    ) {
        this.id = event.getId().toString();
        this.name = event.getName();
        this.organizer = event.getOrganizer().toResponse();
        this.code = event.getCode();
        this.meetingCode = event.getMeetingCode();
        this.createdAt = event.getCreatedAt();
        this.updatedAt = event.getUpdatedAt();
    }
}
