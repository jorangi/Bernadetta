package com.bernadetta.backend.domain.auth.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final long expiration;

    public JwtTokenProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.expiration}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String createToken(Long id, String userId, String role) {
        Date now = new Date();
        Date exprData = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(String.valueOf(id))
                .claim("userId", userId)
                .claim("role", role)
                .issuedAt(now)
                .expiration(exprData)
                .signWith(secretKey)
                .compact();
    }

}
