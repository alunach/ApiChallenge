package com.api.challenge.domain.port.output;

import com.api.challenge.infrastructure.persistence.entity.UserEntity;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<UserEntity> findByEmail(String email);
    UserEntity save(UserEntity user);
}
