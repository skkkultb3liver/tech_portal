package com.dev.blogs4u.content.users.entities;

import com.dev.blogs4u.authentication.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profile_")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
