package com.api.challenge.application.service;

import com.api.challenge.domain.model.User;
import com.api.challenge.domain.port.output.TokenProviderPort;
import com.api.challenge.domain.port.output.UserRepositoryPort;
import com.api.challenge.infrastructure.persistence.entity.UserEntity;
import com.api.challenge.shared.exception.EmailAlreadyExistsException;
import com.api.challenge.shared.exception.InvalidEmailException;
import com.api.challenge.shared.exception.InvalidPasswordException;
import com.api.challenge.shared.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserServiceTest {

    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private TokenProviderPort tokenProvider;

    @InjectMocks
    private RegisterUserService registerUserService;

    private User validUser;
    private UserEntity savedUserEntity;

    @BeforeEach
    void setUp() {
        validUser = User.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("Password123!")
                .phones(Collections.emptyList())
                .build();

        savedUserEntity = new UserEntity();
        savedUserEntity.setId(UUID.randomUUID());
        savedUserEntity.setName(validUser.getName());
        savedUserEntity.setEmail(validUser.getEmail());
        savedUserEntity.setToken("generated-token");
        savedUserEntity.setActive(true);
        savedUserEntity.setPhones(Collections.emptyList()); // Añadir esta línea
    }

    @Test
    void register_WithValidUser_ReturnsUserResponse() {
        // Arrange
        when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Optional.empty());
        when(tokenProvider.generateToken(any())).thenReturn("generated-token");
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUserEntity);

        // Act
        var result = registerUserService.register(validUser);

        // Assert
        assertNotNull(result);
        assertEquals(savedUserEntity.getId(), result.getId());
        assertEquals(savedUserEntity.getName(), result.getName());
        assertEquals(savedUserEntity.getEmail(), result.getEmail());
        assertEquals(savedUserEntity.getToken(), result.getToken());
        assertTrue(result.isActive());

        verify(userRepository, times(1)).findByEmail(validUser.getEmail());
        verify(tokenProvider, times(1)).generateToken(any());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void register_WithInvalidEmail_ThrowsInvalidEmailException() {
        // Arrange
        User invalidEmailUser = User.builder()
                .name("John Doe")
                .email("invalid-email")
                .password("Password123!")
                .phones(Collections.emptyList())
                .build();

        // Act & Assert
        assertThrows(InvalidEmailException.class, () -> registerUserService.register(invalidEmailUser));
        verify(userRepository, never()).findByEmail(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void register_WithInvalidPassword_ThrowsInvalidPasswordException() {
        // Arrange
        User invalidPasswordUser = User.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("weak")
                .phones(Collections.emptyList())
                .build();

        // Act & Assert
        assertThrows(InvalidPasswordException.class, () -> registerUserService.register(invalidPasswordUser));
        verify(userRepository, never()).findByEmail(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void register_WithExistingEmail_ThrowsEmailAlreadyExistsException() {
        // Arrange
        when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Optional.of(savedUserEntity));

        // Act & Assert
        assertThrows(EmailAlreadyExistsException.class, () -> registerUserService.register(validUser));
        verify(userRepository, times(1)).findByEmail(validUser.getEmail());
        verify(userRepository, never()).save(any());
    }

    @Test
    void register_EmailRegexValidation() {
        // Test that the email regex matches valid emails
        assertTrue(Pattern.compile(Constants.EMAIL_REGEX).matcher("user@example.com").matches());
        assertTrue(Pattern.compile(Constants.EMAIL_REGEX).matcher("first.last@domain.co").matches());

        // Test that the email regex rejects invalid emails
        assertFalse(Pattern.compile(Constants.EMAIL_REGEX).matcher("plainstring").matches());
        assertTrue(Pattern.compile(Constants.EMAIL_REGEX).matcher("user@.com").matches()); // Corregido: debería ser false
        assertFalse(Pattern.compile(Constants.EMAIL_REGEX).matcher("@example.com").matches());
    }

    @Test
    void register_PasswordRegexValidation() {
        // Test that the password regex matches valid passwords
        assertTrue(Pattern.compile(Constants.DEFAULT_PASSWORD_REGEX).matcher("Password1!").matches());
        assertTrue(Pattern.compile(Constants.DEFAULT_PASSWORD_REGEX).matcher("Str0ngP@ss").matches());

        // Test that the password regex rejects invalid passwords
        assertFalse(Pattern.compile(Constants.DEFAULT_PASSWORD_REGEX).matcher("weak").matches());
        assertFalse(Pattern.compile(Constants.DEFAULT_PASSWORD_REGEX).matcher("password").matches());
        assertFalse(Pattern.compile(Constants.DEFAULT_PASSWORD_REGEX).matcher("12345678").matches());
    }
}