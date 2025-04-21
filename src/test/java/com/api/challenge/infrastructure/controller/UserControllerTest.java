package com.api.challenge.infrastructure.controller;

import com.api.challenge.application.service.RegisterUserService;
import com.api.challenge.infrastructure.controller.dto.UserRequest;
import com.api.challenge.infrastructure.controller.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private RegisterUserService registerUserService;

    @InjectMocks
    private UserController userController;

    private final UUID TEST_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    private UserRequest createValidRequest() {
        return new UserRequest(
                "John Doe",
                "john.doe@example.com",
                "SecurePass123!",
                Collections.emptyList()
        );
    }

    @Test
    void registerUser_ShouldReturn201Created_WhenRequestIsValid() {
        // Arrange
        UserRequest request = createValidRequest();
        UserResponse mockResponse = UserResponse.builder()
                .id(TEST_UUID)
                .email(request.getEmail())
                .build();

        when(registerUserService.register(any())).thenReturn(mockResponse);

        // Act
        ResponseEntity<UserResponse> response = userController.register(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(TEST_UUID, response.getBody().getId());
        assertEquals(request.getEmail(), response.getBody().getEmail());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals("/api/users/123e4567-e89b-12d3-a456-426614174000", response.getHeaders().getLocation().getPath());

        verify(registerUserService, times(1)).register(any());
    }

}