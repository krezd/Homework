package com.homework.home.controller;

import com.homework.home.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;
    @PostMapping
    String generateJwt(@RequestParam String username){
        return jwtService.generateJwt(username);
    }
}
