package com.github.dankook_univ.meetwork.profile.infra.http.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileUpdateRequest {

	String profileId;
	String nickname;
	String bio;
}
