package com.homework.home.service;

import com.homework.home.dto.AuthResponse;
import com.homework.home.dto.AuthResponseGenerator;
import com.homework.home.dto.TokenRefreshException;
import com.homework.home.dto.request.RegistrationRequest;
import com.homework.home.dto.request.TokensRequest;
import com.homework.home.entity.UserEntity;
import com.homework.home.repository.UserEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshToken refreshToken;
    private final AuthResponseGenerator authResponseGenerator;


    public UserService(UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RefreshToken refreshToken, AuthResponseGenerator authResponseGenerator) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshToken = refreshToken;
        this.authResponseGenerator = authResponseGenerator;
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userEntityRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity users = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username)));
        return org.springframework.security.core.userdetails.User.builder()
                .username(users.getUsername())
                .password(users.getPassword())
                .roles("USER")
                .build();
    }

    public AuthResponse getTokens(String username) {
        UserEntity userEntity = findByUsername(username).orElse(null);
        userEntity.setRefreshToken(refreshToken.generateToken());
        userEntity.setExpiryDate(refreshToken.setExpireDate());
        userEntityRepository.save(userEntity);
        return authResponseGenerator.generateAuthResponse(jwtService.generateJwt(username), userEntity.getRefreshToken());
    }

    public AuthResponse createNewUser(RegistrationRequest registrationRequest) {
        UserEntity userEntity = UserEntity.builder()
                .name(registrationRequest.getName())
                .username(registrationRequest.getUsername())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .refreshToken(refreshToken.generateToken())
                .expiryDate(refreshToken.setExpireDate())
                .build();
        userEntityRepository.save(userEntity);
        return authResponseGenerator.generateAuthResponse(jwtService.generateJwt(registrationRequest.getUsername()), userEntity.getRefreshToken());
    }

    public Boolean validateRefreshToken(String username) {
        UserEntity userEntity = findByUsername(username).orElse(null);
        if (userEntity != null) {
            if (refreshToken.verifyExpiration(userEntity.getExpiryDate())) {
                return true;
            }
            userEntity.setRefreshToken(null);
            userEntity.setExpiryDate(null);
            userEntityRepository.save(userEntity);
            throw new TokenRefreshException(userEntity.getRefreshToken(), "Истек срок действия refreshToken, авторизуйтесь заново");
        }
        return false;
    }
    public void signout(String username){
        UserEntity userEntity = findByUsername(username).orElse(null);
        if(userEntity!=null){
            userEntity.setRefreshToken(null);
            userEntity.setExpiryDate(null);
            userEntityRepository.save(userEntity);
        }
    }

    public AuthResponse refreshTokens(TokensRequest tokensRequest) {
        UserEntity userEntity = findByUsername(jwtService.getUsername(tokensRequest.getAccessToken())).orElse(null);
        if (!tokensRequest.getRefreshToken().equals(userEntity.getRefreshToken())) {
            throw new TokenRefreshException(tokensRequest.getRefreshToken(), "RefreshToken не совпадает с действующим токеном, авторизуйтесь заново");
        }
        userEntity.setRefreshToken(refreshToken.generateToken());
        userEntity.setExpiryDate(refreshToken.setExpireDate());
        userEntityRepository.save(userEntity);
        return authResponseGenerator.generateAuthResponse(jwtService.generateJwt(userEntity.getUsername()), userEntity.getRefreshToken());

    }


}
