package com.api.challenge.domain.port.output;

import com.api.challenge.domain.model.User;

public interface TokenProviderPort {
    String generateToken(String subject);
    boolean validateToken(String token);
    String getSubject(String token);
}
