package com.homework.home.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RefreshToken {
    public Boolean verifyExpiration(LocalDateTime date) {
        LocalDateTime timeNow = LocalDateTime.now();
        return timeNow.isBefore(date);
    }

}
