package com.github.dankook_univ.meetwork.member.infra.persistence;

import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.domain.MemberRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberRepository;

    @Override
    public Optional<Member> getById(UUID memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public Optional<Member> getByEmail(String email) {
        return memberRepository.getByEmail(email);
    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }
}
