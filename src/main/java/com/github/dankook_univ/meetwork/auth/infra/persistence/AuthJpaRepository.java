package com.github.dankook_univ.meetwork.auth.infra.persistence;

import com.github.dankook_univ.meetwork.auth.domain.auth.Auth;
import com.github.dankook_univ.meetwork.auth.domain.auth.AuthType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthJpaRepository extends JpaRepository<Auth, Long> {

    Optional<Auth> findByMemberId(Long memberId);

    Optional<Auth> findByTypeAndClientId(AuthType type, String clientId);
}
