package com.dev.blogs4u.authentication.services;

import com.dev.blogs4u.authentication.entities.UserEntity;

public interface ICustomUserDetailsService {

    UserEntity saveUser(UserEntity user);
    void deleteUser(Long userId);
    UserEntity findUserByJwt(String token);

    UserEntity findUserById(Long userId);

    UserEntity findUserByUid(String uid);

    UserEntity findUserByNickname(String nickname);

    UserEntity findUserByEmail(String email);
}
