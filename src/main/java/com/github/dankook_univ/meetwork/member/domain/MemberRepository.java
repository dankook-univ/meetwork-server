package com.github.dankook_univ.meetwork.member.domain;

import java.util.Optional;

public interface MemberRepository {

    Optional<Member> getById(Long memberId);

    Optional<Member> getByEmail(String email);

    Member save(Member member);
}
