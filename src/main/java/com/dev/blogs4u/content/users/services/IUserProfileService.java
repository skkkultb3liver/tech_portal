package com.dev.blogs4u.content.users.services;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.content.users.entities.UserProfileEntity;
import com.dev.blogs4u.content.users.repositories.UserProfileRepository;

public interface IUserProfileService {

    UserProfileEntity createUserProfile(UserEntity user);

}
