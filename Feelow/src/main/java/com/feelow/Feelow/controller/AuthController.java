package com.feelow.Feelow.controller;

import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.dto.SignInDto;
import com.feelow.Feelow.dto.SignUpDto;
import com.feelow.Feelow.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    // 사용자 가입 / 로그인 요청 처리
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto<?>> signUp(@RequestBody SignUpDto requestBody) {
        // 회원가입 로직 수행
        ResponseDto<?> signUpResponse = authService.signUp(requestBody);

        //  헤더에 추가
        if (signUpResponse.getData() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + signUpResponse.getData());
            return new ResponseEntity<>(signUpResponse, headers, HttpStatus.valueOf(signUpResponse.getStatusCode()));
        }

        return new ResponseEntity<>(signUpResponse, HttpStatus.valueOf(signUpResponse.getStatusCode()));
    }

}