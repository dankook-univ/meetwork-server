package com.github.dankook_univ.meetwork.member.application;

import com.github.dankook_univ.meetwork.member.domain.Member;

public interface MemberService {

    Member getMe(String memberId);

}
