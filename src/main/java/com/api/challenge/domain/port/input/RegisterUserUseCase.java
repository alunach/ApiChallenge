package com.api.challenge.domain.port.input;

import com.api.challenge.domain.model.User;
import com.api.challenge.infrastructure.controller.dto.UserResponse;

public interface RegisterUserUseCase {
    UserResponse register(User user);
}
