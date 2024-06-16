package com.dev.blogs4u.authentication.controllers;

import com.dev.blogs4u.authentication.dto.*;
import com.dev.blogs4u.authentication.services.IAuthenticationService;
import com.dev.blogs4u.authentication.services.ICustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthenticationService authenticationService;
    private final ICustomUserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> signUpHandler(@RequestBody SignUpRequest request) {

        try {
            JwtAuthResponse response = authenticationService.signUp(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> signInHandler(@RequestBody SignInRequest request) {

        try {
            JwtAuthResponse response = authenticationService.signIn(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping("/refresh_token")
    public ResponseEntity<?> refreshTokenHandler(@RequestBody RefreshTokenRequest request) {

        try {
            JwtAuthResponse response = authenticationService.refreshToken(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserHandler(@PathVariable Long userId) {
        userDetailsService.deleteUser(userId);

        return new ResponseEntity<>("Пользователь " + userId + "был успешно удален", HttpStatus.OK);
    }
}
