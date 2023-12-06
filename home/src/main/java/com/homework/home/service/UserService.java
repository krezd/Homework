package com.homework.home.service;


import com.homework.home.entity.UserEntity;
import com.homework.home.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RefreshToken refreshToken;

    public UserService(UserRepository userRepository, RefreshToken refreshToken) {
        this.userRepository = userRepository;
        this.refreshToken = refreshToken;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity users = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username)));
        return org.springframework.security.core.userdetails.User.builder()
                .username(users.getUsername())
                .password(users.getPassword())
                .roles("USER")
                .build();
    }

    public Boolean validateRefreshToken(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElse(null);
        if (userEntity != null) {
            if (refreshToken.verifyExpiration(userEntity.getExpiryDate())) {
                return true;
            }
            userEntity.setRefreshToken(null);
            userEntity.setExpiryDate(null);
            userRepository.save(userEntity);
            return false;
        }
        return false;
    }
}
