package com.github.dankook_univ.meetwork.profile.domain;

import com.github.dankook_univ.meetwork.member.domain.Member;
import java.util.Optional;

public interface ProfileRepository {

    Optional<Profile> getByNickname(String nickname);

    Profile save(Profile profile);

    Optional<Profile> getByMember(Member member);
}
