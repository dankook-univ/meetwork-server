package com.github.dankook_univ.meetwork.member.application;

import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.exceptions.NotFoundMemberException;
import com.github.dankook_univ.meetwork.member.infra.persistence.MemberRepositoryImpl;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepositoryImpl memberRepository;

    @Override
    public Member getById(String memberId) {
        return memberRepository.getById(
            UUID.fromString(memberId)
        ).orElseThrow(NotFoundMemberException::new);
    }

    @Override
    public Optional<Member> getByEmail(String email) {
        return memberRepository.getByEmail(email);
    }
}
