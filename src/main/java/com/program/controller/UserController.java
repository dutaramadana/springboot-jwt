package com.program.controller;

import com.program.dto.UserRegisterDto;
import com.program.dto.UserResponse;
import com.program.dto.UserUpdateDto;
import com.program.dto.WebResponse;
import com.program.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<WebResponse<UserResponse>> profile(Authentication authentication){

        UserResponse userResponse = userService.profile((Integer) authentication.getPrincipal());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        WebResponse.<UserResponse>builder()
                                .data(userResponse)
                                .build()
                );
    }

    @PatchMapping(path = "/profile")
    public ResponseEntity<WebResponse<UserResponse>> update(Authentication authentication,
                                                            @RequestBody UserUpdateDto updateDto){

        UserResponse userResponse = userService.update((Integer) authentication.getPrincipal(), updateDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        WebResponse.<UserResponse>builder()
                                .data(userResponse)
                                .build()
                );
    }

    @PostMapping("/register")
    public ResponseEntity<WebResponse<String>> register(@RequestBody UserRegisterDto register){

        userService.register(register);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        WebResponse.<String>builder()
                                .data("Register Successful")
                                .build()
                );
    }

}
