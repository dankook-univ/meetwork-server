package com.github.dankook_univ.meetwork.auth.domain.auth;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository {

    Auth save(Auth auth);

    Optional<Auth> getById(UUID id);

    Optional<Auth> getByMemberId(UUID memberId);

    Optional<Auth> getByAuthTypeAndClientId(AuthType type, String clientId);
}
