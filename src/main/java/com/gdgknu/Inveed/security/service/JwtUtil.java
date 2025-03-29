package com.gdgknu.Inveed.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey SECRET_KEY;
    @Value("${jwt.access-token-expiration}") private long ACCESS_TOKEN_EXPIRATION;
    @Value("${jwt.refresh-token-expiration}") private long REFRESH_TOKEN_EXPIRATION;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        try {
            this.SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid SECRET_KEY format. Ensure it is Base64-encoded.", e);
        }
    }

    public String createAccessToken(String email) {
        Claims claims = Jwts.claims();
        claims.put("emailId", email);
        claims.put("type", "access");

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("type", "refresh");

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.get("email", String.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = parseToken(token);
            Date expiration = claims.getExpiration();

            return expiration.before(new Date(System.currentTimeMillis()));
        } catch (SignatureException | ExpiredJwtException e) {
            return false;
        }
    }

}
