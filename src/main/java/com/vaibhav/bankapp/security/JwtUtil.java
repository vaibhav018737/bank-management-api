package com.vaibhav.bankapp.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // secret key used to sign the token
    private static final String SECRET = "vaibhav-bankapp-secret-key-which-is-very-long-and-secure";

    // token expiry time - 24 hours in milliseconds
    private static final long EXPIRY = 1000 * 60 * 60 * 24;

    // generate signing key from secret
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // generate token from email
    public String generateToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRY))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    // extract email from token
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // check if token is valid
    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // extract all claims from token
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}