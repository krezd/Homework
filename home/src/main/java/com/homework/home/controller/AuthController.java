package com.homework.home.controller;

import com.homework.home.dto.AppError;
import com.homework.home.dto.AuthResponse;
import com.homework.home.dto.AuthResponseGenerator;
import com.homework.home.dto.request.JwtRequest;
import com.homework.home.dto.request.RegistrationRequest;
import com.homework.home.dto.request.SignoutRequest;
import com.homework.home.dto.request.TokensRequest;
import com.homework.home.service.JwtService;
import com.homework.home.service.RefreshToken;
import com.homework.home.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
   private final AuthResponseGenerator  authResponseGenerator;


    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody JwtRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        return ResponseEntity.ok(userService.getTokens(request.getUsername()));
    }

    @GetMapping("/get")
    public LocalDateTime get(){
        RefreshToken refreshToken = new RefreshToken();

        return refreshToken.setExpireDate();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshTokens(@RequestBody TokensRequest tokensRequest){
        String username = jwtService.getUsername(tokensRequest.getAccessToken());
        if (userService.findByUsername(username).isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Пользователь с указанным именем отсутствует"), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(userService.refreshTokens(tokensRequest));
    }

    @PostMapping("/signout")
    public void signout(@RequestBody SignoutRequest token){
        String username = jwtService.getUsername(token.getAccessToken());
        userService.signout(username);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        if (passwordEncoder.matches(registrationRequest.getPassword(), registrationRequest.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Пароли не совпадают"), HttpStatus.UNAUTHORIZED);
        }
        if (userService.findByUsername(registrationRequest.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Пользователь с указанным именем уже существует"), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(userService.createNewUser(registrationRequest));
    }
}
