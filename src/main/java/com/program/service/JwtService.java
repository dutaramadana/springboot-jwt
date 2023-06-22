package com.program.service;

import com.program.entity.User;

public interface JwtService {

    String generateToken(User user);
    String extractUsername(String token);
    Integer extractId(String token);
}
