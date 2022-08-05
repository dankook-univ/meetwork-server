package com.github.dankook_univ.meetwork.event.infra.http.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EventUpdateRequest {

	String name;
	String code;
	String meetingUrl;
}
