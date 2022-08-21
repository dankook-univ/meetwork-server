package com.github.dankook_univ.meetwork.event.application;

import com.github.dankook_univ.meetwork.board.application.BoardServiceImpl;
import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.board.infra.http.request.BoardCreateRequest;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.exceptions.ExistingCodeException;
import com.github.dankook_univ.meetwork.event.exceptions.NotFoundEventException;
import com.github.dankook_univ.meetwork.event.exceptions.NotFoundEventPermissionException;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventUpdateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.ProfileReleaseRequest;
import com.github.dankook_univ.meetwork.event.infra.persistence.EventRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.exceptions.NotFoundProfileException;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepositoryImpl eventRepository;
    private final ProfileServiceImpl profileService;

    private final BoardServiceImpl boardService;

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
    public List<Profile> getMemberList(String memberId, String eventId, int page) {
        if (!profileService.isEventMember(memberId, eventId)) {
            throw new NotFoundProfileException();
        }

        return profileService.getListByEventId(eventId, PageRequest.of(page - 1, 15));
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
                .meetingUrl(request.getMeetingUrl())
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

        if (request.getCode() != null && checkExistingCode(request.getCode())) {
            throw new ExistingCodeException();
        }

        return event.update(request.getName(), request.getCode(), request.getMeetingUrl());
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
    public Event join(String memberId, String eventId, ProfileCreateRequest request, Boolean isAdmin
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
        Boolean isEventMember = profileService.isEventMember(memberId, eventId);
        if (!isEventMember) {
            throw new NotFoundProfileException();
        }

        profileService.delete(memberId, eventId);
    }

    @Override
    @Transactional
    public void release(String memberId, ProfileReleaseRequest request) {
        Profile profile = profileService.get(memberId, request.getEventId());
        if (!profile.getIsAdmin()) {
            throw new NotFoundEventPermissionException();
        }

        Boolean isEventMember = profileService.isEventMember(
            request.getProfileId(),
            request.getEventId()
        );
        if (!isEventMember) {
            throw new NotFoundProfileException();
        }
        profileService.delete(request.getProfileId(), request.getEventId());
    }

    @Override
    @Transactional
    public void delete(String memberId, String eventId) {
        Event event = getById(eventId);
        if (!event.getOrganizer().getMember().getId().toString().equals(memberId)) {
            throw new NotFoundEventPermissionException();
        }

        List<Board> boards = boardService.getList(memberId, eventId);
        boards.forEach(board -> boardService.delete(memberId, board.getId().toString()));

        profileService.deleteByEventId(eventId);
        eventRepository.delete(event);
    }

    private Event getById(String eventId) {
        return eventRepository.getById(eventId).orElseThrow(NotFoundEventException::new);
    }
}
