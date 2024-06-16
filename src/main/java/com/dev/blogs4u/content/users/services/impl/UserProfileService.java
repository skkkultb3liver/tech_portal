package com.dev.blogs4u.content.users.services.impl;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.content.users.entities.UserProfileEntity;
import com.dev.blogs4u.content.users.services.IUserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService implements IUserProfileService {

    @Override
    public UserProfileEntity createUserProfile(UserEntity user) {

        UserProfileEntity userProfile = UserProfileEntity.builder()
                .user(user)
                .build();

        return userProfile;
    }
}
