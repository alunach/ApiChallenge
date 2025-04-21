package com.api.challenge.domain.port.output;

import com.api.challenge.domain.model.User;

public interface TokenProviderPort {
    String generateToken(User user);
}
