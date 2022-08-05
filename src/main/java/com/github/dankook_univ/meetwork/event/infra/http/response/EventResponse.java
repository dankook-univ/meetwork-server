package com.github.dankook_univ.meetwork.event.infra.http.response;

import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EventResponse {
	UUID id;
	LocalDateTime createdAt;
	LocalDateTime updatedAt;
	String name;
	ProfileResponse organizer;
	String code;
	String meetingUrl;
}
