package com.api.challenge.application.service;

import com.api.challenge.domain.model.User;
import com.api.challenge.domain.port.input.RegisterUserUseCase;
import com.api.challenge.domain.port.output.TokenProviderPort;
import com.api.challenge.domain.port.output.UserRepositoryPort;
import com.api.challenge.infrastructure.controller.dto.PhoneResponse;
import com.api.challenge.infrastructure.controller.dto.UserResponse;
import com.api.challenge.infrastructure.mapper.UserMapper;
import com.api.challenge.infrastructure.persistence.entity.UserEntity;
import com.api.challenge.shared.exception.EmailAlreadyExistsException;
import com.api.challenge.shared.exception.InvalidEmailException;
import com.api.challenge.shared.exception.InvalidPasswordException;
import com.api.challenge.shared.util.Constants;
import jakarta.transaction.Transactional;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.api.challenge.shared.util.Constants.*;

public class RegisterUserService implements RegisterUserUseCase {
    private final UserRepositoryPort userRepository;
    private final TokenProviderPort tokenProvider;

    public RegisterUserService(UserRepositoryPort repo, TokenProviderPort tokenProv) {
        this.userRepository = repo;
        this.tokenProvider = tokenProv;
    }

    @Override
    @Transactional
    public UserResponse register(User user) {
        if (!Pattern.compile(Constants.EMAIL_REGEX)
                .matcher(user.getEmail()).matches()) {
            throw new InvalidEmailException(ERROR_INVALID_EMAIL);
        }

        if (!Pattern.compile(Constants.DEFAULT_PASSWORD_REGEX)
                .matcher(user.getPassword()).matches()) {
            throw new InvalidPasswordException(ERROR_INVALID_PASSWORD);
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(ERROR_EMAIL_EXISTS);
        }

        UserEntity saved = userRepository.save(UserMapper.mapToUserEntity(user, tokenProvider));

        return UserResponse.builder()
                .id(saved.getId())
                .created(saved.getCreated())
                .modified(saved.getModified())
                .lastLogin(saved.getLastLogin())
                .token(saved.getToken())
                .isActive(saved.isActive())
                .name(saved.getName())
                .email(saved.getEmail())
                .phones(saved.getPhones().stream()
                        .map(p -> new PhoneResponse(p.getNumber(), p.getCityCode(), p.getCountryCode()))
                        .collect(Collectors.toList()))
                .build();
    }
}
