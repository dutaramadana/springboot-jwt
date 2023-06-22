package com.program.service.impl;

import com.program.dto.TokenResponse;
import com.program.dto.UserLoginDto;
import com.program.entity.User;
import com.program.repository.UserRepository;
import com.program.service.AuthService;
import com.program.service.JwtService;
import com.program.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private JwtService jwtService;

    @Override
    public TokenResponse login(UserLoginDto loginDto) {

        validationService.validate(loginDto);

        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong"));

        String token = jwtService.generateToken(user);

        if(BCrypt.checkpw(loginDto.getPassword(), user.getPassword())){
            return TokenResponse.builder()
                    .user(user.getEmail())
                    .token(token)
                    .build();
        } else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong");
        }
    }
}
