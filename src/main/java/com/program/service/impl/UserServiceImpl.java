package com.program.service.impl;

import com.program.dto.UserRegisterDto;
import com.program.dto.UserResponse;
import com.program.dto.UserUpdateDto;
import com.program.entity.User;
import com.program.repository.UserRepository;
import com.program.service.UserService;
import com.program.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Override
    @Transactional
    public void register(UserRegisterDto register) {
        validationService.validate(register);

        if(userRepository.existsByEmail(register.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exist");
        }

        User user = new User();
        user.setEmail(register.getEmail());
        user.setPassword(BCrypt.hashpw(register.getPassword(), BCrypt.gensalt()));

        userRepository.save(user);
    }

    @Override
    public UserResponse profile(Integer id) {

        User user = userRepository.findById(id).get();

        return UserResponse.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    @Override
    @Transactional
    public UserResponse update(Integer userId, UserUpdateDto updateDto) {

        validationService.validate(updateDto);

        User user = userRepository.findById(userId).get();

        if(Objects.nonNull(updateDto.getEmail())){
            user.setEmail(updateDto.getEmail());
        }

        if(Objects.nonNull(updateDto.getPassword())){
            user.setPassword(BCrypt.hashpw(updateDto.getPassword(), BCrypt.gensalt()));
        }


        userRepository.save(user);

        return UserResponse.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid user"));
        return (UserDetails) user;
    }
}
