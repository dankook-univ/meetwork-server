package com.github.dankook_univ.meetwork.invitation.application;

import com.github.dankook_univ.meetwork.event.application.EventServiceImpl;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.exceptions.NotFoundEventPermissionException;
import com.github.dankook_univ.meetwork.invitation.domain.Invitation;
import com.github.dankook_univ.meetwork.invitation.exception.AlreadyEventJoinException;
import com.github.dankook_univ.meetwork.invitation.exception.NotFoundInvitationException;
import com.github.dankook_univ.meetwork.invitation.infra.http.request.InvitationCreateRequest;
import com.github.dankook_univ.meetwork.invitation.infra.http.request.InvitationMemberInformation;
import com.github.dankook_univ.meetwork.invitation.infra.persistence.InvitationRepositoryImpl;
import com.github.dankook_univ.meetwork.member.application.MemberServiceImpl;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.exceptions.NotFoundMemberException;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepositoryImpl invitationRepository;

    private final ProfileServiceImpl profileService;

    private final EventServiceImpl eventService;

    private final MemberServiceImpl memberService;

    @Override
    @Transactional
    public Boolean create(String memberId, InvitationCreateRequest request) {
        Profile profile = profileService.get(memberId, request.getEventId());
        if (!profile.getIsAdmin()) {
            throw new NotFoundEventPermissionException();
        }

        Event event = eventService.get(memberId, request.getEventId());

        List<InvitationMemberInformation> list =
            request.getInvitationInformations().stream()
                .filter((it) -> memberService.getByEmail(it.getEmail()).isPresent())
                .map((it) -> InvitationMemberInformation.builder()
                    .member(memberService.getByEmail(it.getEmail())
                        .orElseThrow(NotFoundMemberException::new))
                    .isAdmin(it.getIsAdmin())
                    .build()
                )
                .collect(Collectors.toList());

        for (InvitationMemberInformation it : list) {
            if (invitationRepository.getByGuestIdAndEventId(
                it.getMember().getId().toString(), request.getEventId()
            ).isEmpty()) {
                invitationRepository.save(
                    Invitation.builder()
                        .event(event)
                        .guest(it.getMember())
                        .isAdmin(it.getIsAdmin())
                        .build()
                );
            }
        }
        return true;
    }

    @Override
    public List<Invitation> getList(String memberId) {
        Member member = memberService.getById(memberId);
        return invitationRepository.getList(member.getId().toString());
    }

    @Override
    @Transactional
    public Boolean join(String memberId, String eventId, ProfileCreateRequest request) {
        Invitation invitation = invitationRepository.getByGuestIdAndEventId(memberId, eventId)
            .orElseThrow(NotFoundInvitationException::new);
        invitationRepository.delete(invitation);

        if (profileService.isEventMember(memberId, eventId)) {
            throw new AlreadyEventJoinException();
        }

        eventService.join(
            memberId,
            eventId,
            ProfileCreateRequest.builder()
                .nickname(request.getNickname())
                .bio(request.getBio())
                .profileImage(request.getProfileImage())
                .build(),
            invitation.getIsAdmin()
        );
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(String memberId, String eventId) {
        Invitation invitation = invitationRepository.getByGuestIdAndEventId(memberId, eventId)
            .orElseThrow(NotFoundInvitationException::new);
        invitationRepository.delete(invitation);
        return true;
    }

}
