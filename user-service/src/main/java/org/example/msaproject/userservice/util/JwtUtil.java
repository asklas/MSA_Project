package org.example.msaproject.userservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    private final String secret;
    private final SecretKey key;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secret = secret;
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String getSecretKey() {
        return this.secret;
    }

    public String encodeAccessToken(long minute, Map<String, Object> data) {
        Claims claims = Jwts
                .claims()
                .add("data", data)
                .add("type", "access_token")
                .build();

        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 60 * minute); // 1초 X 60 X

        return Jwts.builder()
                .subject("MSA_project")
                .claims(claims)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    public String encodeRefreshToken(long minute, Map<String, Object> data) {
        Claims claims = Jwts
                .claims()
                .add("data", data)
                .add("type", "refresh_token")
                .build();

        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 60 * minute); // 1초 X 60 X

        return Jwts.builder()
                .subject("MSA_project")
                .claims(claims)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    public Claims decode(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean isTokenExpired(String token) {
        return decode(token).getExpiration().before(new Date());
    }
}
