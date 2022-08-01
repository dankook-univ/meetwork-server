package com.github.dankook_univ.meetwork.auth.infra.persistence;

import com.github.dankook_univ.meetwork.auth.domain.auth.Auth;
import com.github.dankook_univ.meetwork.auth.domain.auth.AuthRepository;
import com.github.dankook_univ.meetwork.auth.domain.auth.AuthType;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl implements AuthRepository {

    private final AuthJpaRepository authJpaRepository;

    @Override
    public Auth save(Auth auth) {
        return authJpaRepository.save(auth);
    }

    @Override
    public Optional<Auth> getById(UUID id) {
        return authJpaRepository.findById(id);
    }

    @Override
    public Optional<Auth> getByMemberId(UUID memberId) {
        return authJpaRepository.findByMemberId(memberId);
    }

    @Override
    public Optional<Auth> getByAuthTypeAndClientId(AuthType type, String clientId) {
        return authJpaRepository.findByTypeAndClientId(type, clientId);
    }
}
