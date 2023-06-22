package com.program.service;

import com.program.dto.TokenResponse;
import com.program.dto.UserLoginDto;

public interface AuthService {

    TokenResponse login(UserLoginDto loginDto);

}
