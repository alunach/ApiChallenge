package com.api.challenge.infrastructure.config;

import com.api.challenge.application.service.RegisterUserService;
import com.api.challenge.domain.port.input.RegisterUserUseCase;
import com.api.challenge.domain.port.output.TokenProviderPort;
import com.api.challenge.domain.port.output.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public RegisterUserUseCase registerUserUseCase(UserRepositoryPort repo, TokenProviderPort tokenProvider) {
        return new RegisterUserService(repo, tokenProvider);
    }
}
