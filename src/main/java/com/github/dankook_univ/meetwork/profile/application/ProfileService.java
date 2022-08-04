package com.github.dankook_univ.meetwork.profile.application;

import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileRequest;

public interface ProfileService {

    Profile create(Member member, ProfileRequest request);

    Profile update(Member member, ProfileRequest request);
}
