package com.github.dankook_univ.meetwork.event.application;

import com.github.dankook_univ.meetwork.board.application.BoardServiceImpl;
import com.github.dankook_univ.meetwork.board.infra.http.request.BoardCreateRequest;
import com.github.dankook_univ.meetwork.chat.infra.persistence.message.ChatMessageRepositoryImpl;
import com.github.dankook_univ.meetwork.chat.infra.persistence.participant.ChatParticipantRepositoryImpl;
import com.github.dankook_univ.meetwork.chat.infra.persistence.room.ChatRoomRepositoryImpl;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.exceptions.ExistingCodeException;
import com.github.dankook_univ.meetwork.event.exceptions.NotFoundEventException;
import com.github.dankook_univ.meetwork.event.exceptions.NotFoundEventPermissionException;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventUpdateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.ProfileReleaseRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.UpdateAdminRequest;
import com.github.dankook_univ.meetwork.event.infra.persistence.EventRepositoryImpl;
import com.github.dankook_univ.meetwork.file.application.file.FileServiceImpl;
import com.github.dankook_univ.meetwork.invitation.infra.persistence.InvitationRepositoryImpl;
import com.github.dankook_univ.meetwork.post.infra.persistence.PostRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.exceptions.NotFoundProfileException;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import com.github.dankook_univ.meetwork.quiz.infra.persistence.QuizRepositoryImpl;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.infra.persistence.QuizParticipantsRepositoryImpl;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepositoryImpl eventRepository;

    private final ProfileServiceImpl profileService;

    private final BoardServiceImpl boardService;

    private final ChatRoomRepositoryImpl chatRoomRepository;

    private final QuizRepositoryImpl quizRepository;

    private final FileServiceImpl fileService;

    private final PostRepositoryImpl postRepository;

    private final QuizParticipantsRepositoryImpl quizParticipantsRepository;

    private final ChatParticipantRepositoryImpl chatParticipantRepository;

    private final ChatMessageRepositoryImpl chatMessageRepository;

    private final InvitationRepositoryImpl invitationRepository;


    @Override
    public Event get(String memberId, String eventId) {
        if (!profileService.isEventMember(memberId, eventId)) {
            throw new NotFoundProfileException();
        }

        return getById(eventId);
    }

    @Override
    public List<Event> getList(String memberId, int page) {
        return profileService.getListByMemberId(memberId, PageRequest.of(page - 1, 15))
            .stream()
            .map(Profile::getEvent)
            .collect(Collectors.toList());
    }

    @Override
    public List<Profile> getMemberList(String memberId, String eventId, Boolean adminOnly,
        int page) {
        if (!profileService.isEventMember(memberId, eventId)) {
            throw new NotFoundProfileException();
        }

        return profileService.getListByEventIdAndAdminOnly(eventId, adminOnly,
            PageRequest.of(page - 1, 15));
    }

    @Override
    public Profile getMember(String memberId, String eventId, String profileId) {
        if (!profileService.isEventMember(memberId, eventId)) {
            throw new NotFoundProfileException();
        }

        return profileService.getById(profileId);
    }

    @Override
    @Transactional
    public Event create(String memberId, EventCreateRequest request) {
        if (checkExistingCode(request.getCode())) {
            throw new ExistingCodeException();
        }

        Event event = eventRepository.save(
            Event.builder()
                .name(request.getName())
                .code(request.getCode())
                .meetingCode(request.getMeetingCode())
                .build()
        );

        event.setOrganizer(
            profileService.create(
                memberId,
                event,
                ProfileCreateRequest.builder()
                    .nickname(request.getOrganizerNickname())
                    .bio(request.getOrganizerBio())
                    .profileImage(request.getOrganizerProfileImage())
                    .build(),
                true
            )
        );

        boardService.automaticBoard().forEach((name, isAdmin) ->
            boardService.create(memberId, BoardCreateRequest.builder()
                .name(name)
                .adminOnly(isAdmin)
                .eventId(event.getId().toString())
                .build()
            )
        );

        return event;
    }

    @Override
    @Transactional
    public Event update(String memberId, String eventId, EventUpdateRequest request) {
        Event event = getById(eventId);
        if (!profileService.get(memberId, eventId).getIsAdmin()) {
            throw new NotFoundEventPermissionException();
        }

        if (!Objects.equals(request.getCode(), event.getCode())
            &&
            checkExistingCode(request.getCode())
        ) {
            throw new ExistingCodeException();
        }

        return event.update(request.getName(), request.getCode(), request.getMeetingCode());
    }

    @Override
    @Transactional
    public Boolean updateAdmin(String memberId, UpdateAdminRequest request) {
        Event event = getById(request.getEventId());
        Profile organizer = profileService.get(memberId, request.getEventId());
        if (event.getOrganizer() != organizer) {
            throw new NotFoundEventPermissionException();
        }

        Profile profile = profileService.getById(request.getProfileId());
        profile.update(null, null, request.getIsAdmin());

        return true;
    }

    @Override
    @Transactional
    public Boolean checkExistingCode(String code) {
        return eventRepository.getByCode(code).isPresent();
    }

    @Override
    @Transactional
    public Event codeJoin(String memberId, String code, ProfileCreateRequest request) {
        Event event = eventRepository.getByCode(code).orElseThrow(NotFoundEventException::new);

        profileService.create(
            memberId,
            getById(event.getId().toString()),
            request,
            false
        );

        return event;
    }

    @Override
    @Transactional
    public Event join(
        String memberId,
        String eventId,
        ProfileCreateRequest request,
        Boolean isAdmin
    ) {
        profileService.create(
            memberId,
            getById(eventId),
            request,
            isAdmin
        );

        return getById(eventId);
    }

    @Override
    public Profile getMyProfile(String memberId, String eventId) {
        return profileService.get(memberId, eventId);
    }

    @Override
    @Transactional
    public void secession(String memberId, String eventId) {
        Profile target = profileService.get(memberId, eventId);
        deleteAll(target);
    }

    @Override
    @Transactional
    public void release(String memberId, ProfileReleaseRequest request) {
        Profile profile = profileService.get(memberId, request.getEventId());
        if (!profile.getIsAdmin()) {
            throw new NotFoundEventPermissionException();
        }

        Profile target = profileService.getById(request.getProfileId());
        deleteAll(target);
    }

    @Override
    @Transactional
    public void delete(String memberId, String eventId) {
        Event event = getById(eventId);
        if (!event.getOrganizer().getMember().getId().toString().equals(memberId)) {
            throw new NotFoundEventPermissionException();
        }

        boardService.deleteByEventId(eventId);
        chatRoomRepository.deleteByEventId(eventId);
        quizRepository.deleteByEventId(eventId);
        invitationRepository.deleteByEventId(eventId);
        eventRepository.delete(event);
        profileService.deleteByEventId(eventId);
    }

    private Event getById(String eventId) {
        return eventRepository.getById(eventId).orElseThrow(NotFoundEventException::new);
    }

    private void deleteAll(Profile profile) {
        fileService.deleteByUploaderId(profile.getMember().getId().toString());
        postRepository.deleteByWriterId(profile.getId().toString());
        quizParticipantsRepository.deleteByProfileId(profile.getId().toString());
        chatParticipantRepository.deleteByMemberId(profile.getId().toString());
        chatMessageRepository.deleteBySenderId(profile.getId().toString());
        chatRoomRepository.deleteByOrganizerId(profile.getId().toString());
        profileService.delete(profile);
    }
}
