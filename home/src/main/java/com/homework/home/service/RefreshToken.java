package com.homework.home.service;

import com.homework.home.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshToken {

    @Value("${jwt.refreshTtl}")
    private long refreshTtl;

    public String generateToken(){
        return UUID.randomUUID().toString();
    }

    public LocalDateTime setExpireDate(){
        LocalDateTime now = LocalDateTime.now().plusSeconds(refreshTtl);
        return now;
    }

    public Boolean verifyExpiration(LocalDateTime date) {
        LocalDateTime timeNow = LocalDateTime.now();
        return timeNow.isBefore(date);
    }

}
