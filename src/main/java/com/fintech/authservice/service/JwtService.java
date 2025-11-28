package com.fintech.authservice.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fintech.authservice.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {

	private final SecretKey key;

    private final long expirationMs;
    
    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration:900000}") long expirationMs // default 15m
        ) {
            if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
                throw new IllegalArgumentException("JWT secret must be at least 32 bytes long");
            }
            this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            this.expirationMs = expirationMs;
        }


	public String generateToken(UserEntity userdetails) {
		
		
		
		Map<String,Object> claims = new HashMap<>();
		claims.put("roles",userdetails.getRoles());
		claims.put("username",userdetails.getFullName());
		claims.put("userId", userdetails.getId());
		long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expiry = new Date(now + expirationMs);

		JwtBuilder builder = Jwts.builder()
				
				
	                .setSubject(userdetails.getEmail())
	                .setIssuedAt(issuedAt)
	                .setExpiration(expiry)
	                .addClaims(claims)
	                .signWith(key, SignatureAlgorithm.HS256);

		
		return builder.compact();
		// TODO Auto-generated method stub
		
	}

	public long getExpirationMillis() {
		// TODO Auto-generated method stub
		return this.expirationMs;
	}
	
	public boolean validateToken(String token) {
	    try {
	        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
	        return true;
	    } catch (Exception ex) {
	        return false;
	    }
	}

	public String extractEmail(String token) {
	    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	    return claims.getSubject();
	}


}
