package com.github.dankook_univ.meetwork.event.infra.http.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EventCreateRequest {

	@NotNull
	@NotEmpty
	String name;

	@NotNull
	@NotEmpty
	String code;

	String meetingCode;

	@NotNull
	@NotEmpty
	String organizerNickname;

	String organizerBio;

	MultipartFile organizerProfileImage;

}
