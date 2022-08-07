package com.github.dankook_univ.meetwork.event.application;

import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.exceptions.ExistingCodeException;
import com.github.dankook_univ.meetwork.event.exceptions.NotFoundEventException;
import com.github.dankook_univ.meetwork.event.exceptions.NotFoundEventPermissionException;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventUpdateRequest;
import com.github.dankook_univ.meetwork.event.infra.persistence.EventRepositoryImpl;
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
public class EventServiceImpl implements EventService {

    private final EventRepositoryImpl eventRepository;
    private final ProfileServiceImpl profileService;

    @Override
    public Event get(String memberId, String eventId) {
        if (profileService.get(memberId, eventId) == null) {
            throw new NotFoundEventPermissionException();
        }

        return getById(eventId);
    }

    @Override
    public List<Event> getList(String memberId) {
        return profileService.getListByMemberId(memberId)
            .stream()
            .map(Profile::getEvent)
            .collect(Collectors.toList());
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

        return event.setOrganizer(
            profileService.create(
                memberId,
                event,
                request.getOrganizer(),
                true
            )
        );
    }

    @Override
    @Transactional
    public Event update(String memberId, String eventId, EventUpdateRequest request) {
        if (checkExistingCode(request.getCode())) {
            throw new ExistingCodeException();
        }

        Event event = getById(eventId);
        if (!profileService.get(memberId, eventId).getIsAdmin()) {
            throw new NotFoundEventPermissionException();
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

        return join(memberId, event.getId().toString(), request);
    }

    @Override
    @Transactional
    public Event join(String memberId, String eventId, ProfileCreateRequest request) {
        profileService.create(
            memberId,
            getById(eventId),
            request,
            false
        );

        return getById(eventId);
    }

    @Override
    @Transactional
    public void secession(String memberId, String eventId) {
        profileService.delete(memberId, eventId);
    }

    @Override
    @Transactional
    public void delete(String memberId, String eventId) {
        Event event = getById(eventId);
        if (!event.getOrganizer().getMember().getId().toString().equals(memberId)) {
            throw new NotFoundEventPermissionException();
        }

        eventRepository.delete(eventId);
    }

    private Event getById(String eventId) {
        return eventRepository.getById(eventId).orElseThrow(NotFoundEventException::new);
    }
}
