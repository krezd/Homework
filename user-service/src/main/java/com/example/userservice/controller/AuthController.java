package com.example.userservice.controller;

import com.example.userservice.dto.AppError;
import com.example.userservice.dto.AuthResponseGenerator;
import com.example.userservice.dto.request.*;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.service.JwtService;
import com.example.userservice.service.RefreshToken;
import com.example.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthResponseGenerator authResponseGenerator;


    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid JwtRequest request) {
        UserEntity user = userService.findByUsername(request.getUsername()).orElse(null);
        if(user == null){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(userService.getTokens(request.getUsername()));
    }

    @GetMapping("/get")
    public LocalDateTime get(){
        RefreshToken refreshToken = new RefreshToken();
        return refreshToken.setExpireDate();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshTokens(@RequestBody TokensRequest tokensRequest,HttpServletRequest request){
        String username = null;
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            username = jwtService.getUsername(authHeader);
        }
        if (!userService.validateRefreshToken(username,tokensRequest.getRefreshToken())){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "RefreshToken не действителен"), HttpStatus.UNAUTHORIZED);
        }
        if (userService.findByUsername(username).isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Пользователь с указанным именем отсутствует"), HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(userService.refreshTokens(tokensRequest,authHeader));
    }

    @PostMapping("/signout")
    public void signout(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String username = jwtService.getUsername(authHeader);
        userService.signout(username);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody DeleteRequest delRequest, HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String username = jwtService.getUsername(authHeader);
            UserEntity user = userService.findByUsername(username).orElse(null);
            if(passwordEncoder.matches(delRequest.getPassword(), user.getPassword())){
                userService.delete(user.getUserID());
            }
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        if (!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registrationRequest.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userService.createNewUser(registrationRequest));
    }
}

