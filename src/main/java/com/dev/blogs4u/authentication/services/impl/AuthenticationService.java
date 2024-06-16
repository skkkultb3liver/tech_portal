package com.dev.blogs4u.authentication.services.impl;

import com.dev.blogs4u.authentication.dto.*;
import com.dev.blogs4u.authentication.entities.RefreshTokenEntity;
import com.dev.blogs4u.authentication.entities.RoleEntity;
import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.authentication.services.IAuthenticationService;
import com.dev.blogs4u.authentication.services.ICustomUserDetailsService;
import com.dev.blogs4u.authentication.services.IRefreshTokenService;
import com.dev.blogs4u.authentication.services.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService implements IAuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IRefreshTokenService refreshTokenService;
    private final ICustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    private JwtAuthResponse authenticateUser(String userEmail, String userPassword) {

        try {

            log.info("[AuthenticationService] Try to authenticate");

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userEmail, userPassword)
            );

            var user = userDetailsService.findUserByEmail(userEmail);
            var jwt = jwtService.generateToken(user);
            var refreshToken = refreshTokenService.generateToken(userEmail);

            return JwtAuthResponse.builder().accessToken(jwt).refreshToken(refreshToken.getToken()).build();

        }
        catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Неверная почта или пароль");
        }

    }

    @Override
    public JwtAuthResponse signIn(SignInRequest request) {

        if (request.getUsername().contains("@")) {

            log.info("[AuthenticationService] Try to auth with email {}", request.getUsername());
            return authenticateUser(request.getUsername(), request.getPassword());

        } else {

            log.info("[AuthenticationService] Try to auth with nickname {}", request.getUsername());

            UserEntity user = userDetailsService.findUserByNickname(request.getUsername());
            return authenticateUser(user.getEmail(), request.getPassword());
        }
    }

    @Override
    public JwtAuthResponse signUp(SignUpRequest request) {

        UserEntity createdUSer = UserEntity.builder()
                .email(request.getUserEmail())
                .nickname(request.getNickname())
                .uid("@" + request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleEntity.ROLE_USER)
                .build();

        log.info("AuthenticationService: try to register user");

        try {

            UserEntity savedUser = userDetailsService.saveUser(createdUSer);
            var jwt = jwtService.generateToken(savedUser);

            return JwtAuthResponse.builder().accessToken(jwt).build();
        }
        catch (Exception e) {
            throw new DataIntegrityViolationException("Пользователь с такой почтой или логином уже существет");
        }
    }

    @Override
    public JwtAuthResponse refreshToken(RefreshTokenRequest request) {

        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::validateToken)
                .map(RefreshTokenEntity::getUser)
                .map(user ->
                    {
                        var jwt = jwtService.generateToken(user);

                        return JwtAuthResponse.builder()
                                .accessToken(jwt)
                                .refreshToken(request.getRefreshToken())
                                .build();
                    }
                ).orElseThrow(
                        () -> new UsernameNotFoundException("Не удалось найти пользователя")
                );
    }
}
