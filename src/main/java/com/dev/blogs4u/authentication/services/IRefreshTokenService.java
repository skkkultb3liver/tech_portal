package com.dev.blogs4u.authentication.services;

import com.dev.blogs4u.authentication.entities.RefreshTokenEntity;

import java.util.Optional;

public interface IRefreshTokenService {

    RefreshTokenEntity generateToken(String userEmail);

    Optional<RefreshTokenEntity> findByToken(String token);
    RefreshTokenEntity validateToken(RefreshTokenEntity token);

}
