package com.homework.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordController {
    private final PasswordEncoder passwordEncoder;
    @PostMapping
    String createPassword(@RequestParam String password){
        return passwordEncoder.encode(password);
    }
    @PostMapping("/matches")
    public Boolean checkPassword(@RequestParam String password, String passwordHash){
        return passwordEncoder.matches(password,passwordHash);
    }


}
