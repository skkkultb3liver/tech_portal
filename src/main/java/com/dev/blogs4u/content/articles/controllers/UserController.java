package com.dev.blogs4u.content.articles.controllers;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.authentication.services.ICustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class UserController {

    private final ICustomUserDetailsService userDetailsService;

    @GetMapping("/im")
    public ResponseEntity<UserEntity> getUserProfileHandler(@RequestHeader("Authorization") String token) {
        UserEntity user = userDetailsService.findUserByJwt(token);

        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getOtherUserProfileHAndler(@PathVariable Long userId) {
        UserEntity user = userDetailsService.findUserById(userId);

        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

}
