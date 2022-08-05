package com.github.dankook_univ.meetwork.event.infra.persistence;

import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.domain.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {
	private final EventJpaRepository eventJpaRepository;

	@Override
	public Optional<Event> getById(String eventId) {
		return eventJpaRepository.findById(UUID.fromString(eventId));
	}

	@Override
	public Event save(Event event) {
		return eventJpaRepository.save(event);
	}

	@Override
	public void delete(String eventId) {
		eventJpaRepository.deleteById(UUID.fromString(eventId));
	}
}
