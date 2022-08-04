package com.github.dankook_univ.meetwork.profile.application;

import com.github.dankook_univ.meetwork.member.application.MemberServiceImpl;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.exceptions.ExistingNicknameException;
import com.github.dankook_univ.meetwork.profile.exceptions.NotFoundProfileException;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileRequest;
import com.github.dankook_univ.meetwork.profile.infra.persistence.ProfileRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepositoryImpl profileRepository;
    private final MemberServiceImpl memberService;

    @Override
    @Transactional
    public Profile create(Member member, ProfileRequest request) {
        Profile existingNickname = profileRepository.getByNickname(request.getNickname())
            .orElse(null);
        if (existingNickname != null) {
            throw new ExistingNicknameException();
        }

        return profileRepository.save(
            Profile.builder()
                .member(member)
                .nickname(request.getNickname())
                .bio(request.getBio())
                .isAdmin(request.getIsAdmin())
                .build()
        );
    }

    @Override
    @Transactional
    public Profile update(Member member, ProfileRequest request) {
        // eventId와 함께 조회해야함 findByMemberIdAndEventId
        Profile profile = profileRepository.getByMember(member)
            .orElseThrow(NotFoundProfileException::new);

        profile.update(request.getNickname(), request.getBio());
        return profile;
    }
}
