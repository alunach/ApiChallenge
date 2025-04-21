package com.api.challenge.infrastructure.mapper;

import com.api.challenge.domain.model.Phone;
import com.api.challenge.domain.model.User;
import com.api.challenge.domain.port.output.TokenProviderPort;
import com.api.challenge.infrastructure.controller.dto.PhoneRequest;
import com.api.challenge.infrastructure.controller.dto.UserRequest;
import com.api.challenge.infrastructure.persistence.entity.PhoneEntity;
import com.api.challenge.infrastructure.persistence.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toDomain(UserRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .phones(request.getPhones().stream()
                        .map(UserMapper::toDomain)
                        .collect(Collectors.toList()))
                .build();
    }

    private static Phone toDomain(PhoneRequest phoneRequest) {
        return Phone.builder()
                .number(phoneRequest.getNumber())
                .cityCode(phoneRequest.getCityCode())
                .countryCode(phoneRequest.getCountryCode())
                .build();
    }

    public static UserEntity mapToUserEntity(User user, TokenProviderPort tokenProvider) {
        LocalDateTime now = LocalDateTime.now();
        String token = tokenProvider.generateToken(user.getEmail());

        return UserEntity.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phones(user.getPhones().stream()
                        .map(p -> PhoneEntity.builder()
                                .number(p.getNumber())
                                .cityCode(p.getCityCode())
                                .countryCode(p.getCountryCode())
                                .build())
                        .collect(Collectors.toList()))
                .created(now)
                .modified(now)
                .lastLogin(now)
                .token(token)
                .isActive(true)
                .build();
    }
}
