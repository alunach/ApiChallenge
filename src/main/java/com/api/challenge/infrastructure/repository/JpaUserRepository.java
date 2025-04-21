package com.api.challenge.infrastructure.repository;

import com.api.challenge.infrastructure.persistence.entity.UserEntity;
import com.api.challenge.domain.port.output.SpringDataUserRepository;
import com.api.challenge.domain.port.output.UserRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepositoryPort {
    private final SpringDataUserRepository repository;

    public JpaUserRepository(SpringDataUserRepository repo) {
        this.repository = repo;
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public UserEntity save(UserEntity user) {
        return repository.save(user);
    }
}
