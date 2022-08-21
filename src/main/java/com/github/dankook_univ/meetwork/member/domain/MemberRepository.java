package com.github.dankook_univ.meetwork.member.domain;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {

    Optional<Member> getById(UUID memberId);

    Optional<Member> getByEmail(String email);

    Member save(Member member);
}
