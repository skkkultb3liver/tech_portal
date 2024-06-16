package com.dev.blogs4u.authentication.services.impl;

import com.dev.blogs4u.authentication.entities.RefreshTokenEntity;
import com.dev.blogs4u.authentication.repositories.RefreshTokenRepository;
import com.dev.blogs4u.authentication.repositories.UserRepository;
import com.dev.blogs4u.authentication.services.IRefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenService implements IRefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${security.refresh_token.lifetime}")
    private Long REFRESH_TOKEN_LIFETIME;

    @Override
    public RefreshTokenEntity generateToken(String userEmail) {

        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .user(userRepository.findByEmail(userEmail).get())
                .token(UUID.randomUUID().toString())
                .dateOfExpire(Instant.now().plus(Duration.ofMillis(REFRESH_TOKEN_LIFETIME)))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshTokenEntity> findByToken(String token) {

        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshTokenEntity validateToken(RefreshTokenEntity token) {

        if (token.getDateOfExpire().compareTo(Instant.now()) < 0) {
            deleteRefreshToken(token);

            throw new RuntimeException("Ваша сессия окончена. Пожалуйста, авторизуйтесь повторно " + token.getToken());
        }

        return token;
    }

    private void deleteRefreshToken(RefreshTokenEntity token) {
        refreshTokenRepository.delete(token);
    }

    private void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
