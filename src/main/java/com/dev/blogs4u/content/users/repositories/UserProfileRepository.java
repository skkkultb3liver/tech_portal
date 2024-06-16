package com.dev.blogs4u.content.users.repositories;

import com.dev.blogs4u.content.users.entities.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {

    @Query(
            "select up from UserProfileEntity up where up.user.id = :userId"
    )
    Optional<UserProfileEntity> getByUserId(@Param("userId") Long userId);

    @Query(
            "select up from UserProfileEntity up where up.user.uid = :userUid"
    )
    Optional<UserProfileEntity> getByUserUid(@Param("userUid") Long userUid);

}
