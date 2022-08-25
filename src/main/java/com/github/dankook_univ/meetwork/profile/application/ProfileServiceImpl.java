package com.github.dankook_univ.meetwork.profile.application;

import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.file.application.file.FileServiceImpl;
import com.github.dankook_univ.meetwork.file.domain.File;
import com.github.dankook_univ.meetwork.file.domain.FileType;
import com.github.dankook_univ.meetwork.member.application.MemberServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.exceptions.ExistingNicknameException;
import com.github.dankook_univ.meetwork.profile.exceptions.NotFoundProfileException;
import com.github.dankook_univ.meetwork.profile.exceptions.NotFoundProfilePermissionException;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileUpdateRequest;
import com.github.dankook_univ.meetwork.profile.infra.persistence.ProfileRepositoryImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepositoryImpl profileRepository;
    private final MemberServiceImpl memberService;
    private final FileServiceImpl fileService;

    @Override
    public Profile get(String memberId, String eventId) {
        return profileRepository.getByMemberIdAndEventId(memberId, eventId)
            .orElseThrow(NotFoundProfileException::new);
    }

    @Override
    public Profile getById(String profileId) {
        return profileRepository.getById(profileId).orElseThrow(NotFoundProfileException::new);
    }

    @Override
    @Transactional
    public Profile create(
        String memberId,
        Event event,
        ProfileCreateRequest request,
        Boolean isAdmin
    ) {
        if (
            profileRepository.getByEventIdAndNickname(
                event.getId().toString(),
                request.getNickname()
            ).isPresent()
        ) {
            throw new ExistingNicknameException();
        }

        Profile profile = profileRepository.save(
            Profile.builder()
                .member(memberService.getById(memberId))
                .event(event)
                .nickname(request.getNickname())
                .bio(request.getBio())
                .isAdmin(isAdmin)
                .build()
        );

        if (request.getProfileImage() != null) {
            profile.updateProfileImage(
                fileService.upload(memberId, FileType.profile, request.getProfileImage())
            );
        }

        return profile;
    }

    @Override
    @Transactional
    public Profile update(String memberId, ProfileUpdateRequest request) {
        Profile profile = profileRepository.getById(request.getProfileId())
            .orElseThrow(NotFoundProfileException::new);
        if (!profile.getMember().getId().toString().equals(memberId)) {
            throw new NotFoundProfilePermissionException();
        }

        profile.update(request.getNickname(), request.getBio(), null);

        if (request.getProfileImage() != null) {
            if (profile.getProfileImage() != null) {
                fileService.delete(profile.getProfileImage().getId().toString());
            }

            File file = fileService.upload(memberId, FileType.profile, request.getProfileImage());
            profile.updateProfileImage(file);
        }

        if (request.getIsProfileImageDeleted() && profile.getProfileImage() != null) {
            fileService.delete(profile.getProfileImage().getId().toString());
        }

        return profile;
    }

    @Override
    @Transactional
    public void delete(String memberId, String eventId) {
        profileRepository.delete(memberId, eventId);
    }

    @Override
    public void delete(Profile profile) {
        profileRepository.delete(profile);
    }

    @Override
    @Transactional
    public void deleteByEventId(String eventId) {
        profileRepository.deleteAllByEventId(eventId);
    }

    @Override
    public List<Profile> getListByMemberId(String memberId, Pageable pageable) {
        return profileRepository.getByMemberId(memberId, pageable);
    }

    @Override
    public List<Profile> getListByEventIdAndAdminOnly(String eventId, Boolean adminOnly,
        Pageable pageable) {
        return profileRepository.getByEventIdAndAdminOnly(eventId, adminOnly, pageable);
    }

    @Override
    public Boolean isEventMember(String memberId, String eventId) {
        return profileRepository.getByMemberIdAndEventId(memberId, eventId).isPresent();
    }
}
