package com.richardz02.personal_finance.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

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

    // Validate a jwt
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes())).build().parseSignedClaims(token); 
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}