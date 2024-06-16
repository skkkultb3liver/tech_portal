package com.dev.blogs4u.authentication.services.impl;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.authentication.repositories.UserRepository;
import com.dev.blogs4u.authentication.services.ICustomUserDetailsService;
import com.dev.blogs4u.authentication.services.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements ICustomUserDetailsService, UserDetailsService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Пользователя с такой почтой не существует")
        );
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        log.info("CustomUSerDetailsService: TRY TO SAVE USER");
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserEntity findUserByJwt(String token) {
        String jwt = token.substring(7);
        String userEmail = jwtService.extractUsername(jwt);

        return userRepository.findByEmail(userEmail).orElseThrow(
                () -> new UsernameNotFoundException("Не удалось найти пользователя")
        );
    }

    @Override
    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("Не удалось найти пользователя")
        );
    }

    @Override
    public UserEntity findUserByUid(String uid) {
        return userRepository.findByUid(uid).orElseThrow(
                () -> new UsernameNotFoundException("Не удалось найти пользователя")
        );
    }

    @Override
    public UserEntity findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElseThrow(
                () -> new UsernameNotFoundException("Не удалось найти пользователя")
        );
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Не удалось найти пользователя")
        );
    }
}
