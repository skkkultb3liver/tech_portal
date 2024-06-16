package com.dev.blogs4u.authentication.services;

import com.dev.blogs4u.authentication.dto.*;

public interface IAuthenticationService {

    JwtAuthResponse signIn(SignInRequest request);
    JwtAuthResponse signUp(SignUpRequest request);
    JwtAuthResponse refreshToken(RefreshTokenRequest request);

}
