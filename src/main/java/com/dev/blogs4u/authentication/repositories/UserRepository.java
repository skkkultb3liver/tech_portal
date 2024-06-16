package com.dev.blogs4u.authentication.repositories;

import com.dev.blogs4u.authentication.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUid(String uid);
    Optional<UserEntity> findByNickname(String nickname);
}
