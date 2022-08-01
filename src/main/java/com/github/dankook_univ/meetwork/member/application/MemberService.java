package com.github.dankook_univ.meetwork.member.application;

import com.github.dankook_univ.meetwork.member.infra.http.response.MemberResponse;

public interface MemberService {

    MemberResponse getMe(String memberId);

}
