package com.github.dankook_univ.meetwork.event.infra.http.request;

import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EventCreateRequest {

	@NotNull
	@NotEmpty
	String name;

	@NotNull
	ProfileCreateRequest organizer;

	@NotNull
	@NotEmpty
	String code;

	String meetingUrl;
}
