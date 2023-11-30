package com.feelow.Feelow.controller;

import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.dto.SignInDto;
import com.feelow.Feelow.dto.SignUpDto;
import com.feelow.Feelow.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    // 사용자 가입 / 로그인 요청 처리
    @PostMapping("/sign-up")
    public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody){
        return authService.signUp(requestBody);
    }


}