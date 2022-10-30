package com.github.dankook_univ.meetwork.auth.domain.auth;

import java.util.Optional;

public interface AuthRepository {

    Auth save(Auth auth);

    Optional<Auth> getById(Long id);

    Optional<Auth> getByMemberId(Long memberId);

    Optional<Auth> getByAuthTypeAndClientId(AuthType type, String clientId);
}
