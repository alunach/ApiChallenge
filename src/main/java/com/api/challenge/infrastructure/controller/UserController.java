package com.api.challenge.infrastructure.controller;

import com.api.challenge.domain.model.User;
import com.api.challenge.domain.port.input.RegisterUserUseCase;
import com.api.challenge.infrastructure.controller.dto.UserRequest;
import com.api.challenge.infrastructure.controller.dto.UserResponse;
import com.api.challenge.infrastructure.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;

    public UserController(RegisterUserUseCase useCase) {
        this.registerUserUseCase = useCase;
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        User user = UserMapper.toDomain(request);
        UserResponse response = registerUserUseCase.register(user);
        return ResponseEntity.ok(response);
    }
}
