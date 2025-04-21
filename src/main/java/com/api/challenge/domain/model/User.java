package com.api.challenge.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;

    // Reglas de negocio aquí, por ejemplo:
    public void activate() {
        this.isActive = true;
        this.modified = LocalDateTime.now();
    }

    // Getters/setters o métodos propios
}