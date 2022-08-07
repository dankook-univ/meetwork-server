package com.github.dankook_univ.meetwork.event.infra.persistence;

import com.github.dankook_univ.meetwork.event.domain.Event;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<Event, UUID> {

	void deleteById(@NonNull UUID eventId);

	Optional<Event> findByCode(String code);
}
