package com.richardz02.personal_finance.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

@Service
public class JwtService {
    // Read secret key, expiration, and issuer from application.properties file
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration; // Relative duration of jwt (15 minutes)

    @Value("${jwt.issuer}")
    private String issuer;
    
    // Generate a jwt, and set payload 
    public String generateToken(String userId, String username) {
        long now = System.currentTimeMillis(); // Get current time in milliseconds
        Date issuedAt = new Date(now); // Create Date object for now
        Date expirationDate = new Date(now + expiration); // Create Date object for expiration date

        // Build and return jwt
        return Jwts.builder()
                    .issuer(issuer)
                    .subject(userId)
                    .claim("name", username)
                    .issuedAt(issuedAt)
                    .expiration(expirationDate)
                    .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                    .compact();
    }

    // Extract username from the token
    public String extractUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();

            String username = claims.get("name", String.class);
            return username;
        } catch (JwtException e) {
            return null;
        }
    }

    // Validate a jwt and extract user id from the token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes())).build().parseSignedClaims(token); 
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}