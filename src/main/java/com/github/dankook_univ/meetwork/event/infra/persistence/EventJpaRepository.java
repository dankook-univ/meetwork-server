package com.github.dankook_univ.meetwork.event.infra.persistence;

import com.github.dankook_univ.meetwork.event.domain.Event;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByCode(String code);

    void deleteById(@NonNull Long eventId);
}
