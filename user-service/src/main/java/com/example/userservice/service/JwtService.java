package com.example.userservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.tokenTtl}")
    private long ttl;

    public String generateJwt(String username){
        Date now = new Date();
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(new Date(now.getTime() + ttl*1000))
                .sign(Algorithm.HMAC256((secret)));
    }
    public String getUsername(String token){

        DecodedJWT decodedJWT = JWT.decode(token);
        Claim claim = decodedJWT.getClaim("username");
        return claim.asString();
    }
    public boolean isTokenExpired(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token);
            return false;
        } catch (TokenExpiredException ex) {
            return true;
        }
    }

}

