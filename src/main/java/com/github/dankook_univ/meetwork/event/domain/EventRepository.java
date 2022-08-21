package com.github.dankook_univ.meetwork.event.domain;

import java.util.Optional;

public interface EventRepository {

    Optional<Event> getById(String eventId);

    Event save(Event event);

    Optional<Event> getByCode(String code);

    void delete(Event event);
}
