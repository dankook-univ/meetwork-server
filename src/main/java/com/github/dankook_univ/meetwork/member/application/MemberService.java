package com.github.dankook_univ.meetwork.member.application;

import com.github.dankook_univ.meetwork.member.domain.Member;
import java.util.Optional;

public interface MemberService {

    Member getById(String memberId);

    Optional<Member> getByEmail(String email);

}
