package com.feelow.Feelow.controller;

import com.feelow.Feelow.domain.dto.ResponseDto;
import com.feelow.Feelow.domain.dto.SignUpDto;
import com.feelow.Feelow.jwt.MemberInfo;
import com.feelow.Feelow.jwt.TokenProvider;
import com.feelow.Feelow.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    private TokenProvider tokenProvider;

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

    @GetMapping("/")
    public ResponseEntity<MemberInfo> getMemberInfo(HttpServletRequest request) {
        // 헤더에서 JWT를 추출
        String token = extractTokenFromHeader(request);

        if (token != null) {
            // JWT를 이용하여 MemberInfo 객체를 얻음
            MemberInfo memberInfo = tokenProvider.getMemberInfo(token);
            return new ResponseEntity<>(memberInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        // Authorization 헤더에서 Bearer 토큰을 추출
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }



}