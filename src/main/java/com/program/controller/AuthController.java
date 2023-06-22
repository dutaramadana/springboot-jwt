package com.program.controller;

import com.program.dto.TokenResponse;
import com.program.dto.UserLoginDto;
import com.program.dto.WebResponse;
import com.program.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<WebResponse<TokenResponse>> login(@RequestBody UserLoginDto loginDto){

        TokenResponse login = authService.login(loginDto);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(WebResponse.<TokenResponse>builder()
                        .data(login)
                        .build());
    }
}
