package com.github.dankook_univ.meetwork.member.application;

import com.github.dankook_univ.meetwork.member.domain.Member;

public interface MemberService {

    Member getById(String memberId);

    Member getByEmail(String email);

}
