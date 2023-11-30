package com.feelow.Feelow.controller;

import com.feelow.Feelow.dto.*;
import com.feelow.Feelow.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<SignUpResponseDto>> signUp(@RequestBody SignUpDto signUpDto) {
        ResponseDto<SignUpResponseDto> response = authService.signIn(signUpDto)

        if (response.isResult()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
