package com.program.service;

import com.program.dto.UserRegisterDto;
import com.program.dto.UserResponse;
import com.program.dto.UserUpdateDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(UserRegisterDto register);
    UserResponse profile(Integer id);
    UserResponse update(Integer userId, UserUpdateDto updateDto);
}
