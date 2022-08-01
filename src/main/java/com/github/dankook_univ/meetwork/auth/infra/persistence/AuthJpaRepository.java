package com.github.dankook_univ.meetwork.auth.infra.persistence;

import com.github.dankook_univ.meetwork.auth.domain.auth.Auth;
import com.github.dankook_univ.meetwork.auth.domain.auth.AuthType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthJpaRepository extends JpaRepository<Auth, UUID> {

    Optional<Auth> findByMemberId(UUID memberId);

    Optional<Auth> findByTypeAndClientId(AuthType type, String clientId);
}
