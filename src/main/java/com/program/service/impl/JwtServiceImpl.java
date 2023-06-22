package com.program.service.impl;

import com.program.entity.User;
import com.program.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${app.security.jwt.secret-key}")
    private String jwtSecret;

    @Value("${app.security.jwt.expiration}")
    private long jwtExpiration;

    @Override
    public String generateToken(User user) {
        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(
                        Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret))
                )
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret))
                )
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Override
    public Integer extractId(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret))
                )
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", Integer.class);
    }
}
