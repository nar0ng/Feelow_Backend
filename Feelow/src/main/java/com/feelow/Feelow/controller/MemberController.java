package com.feelow.Feelow.controller;

import com.feelow.Feelow.dto.SignInDto;
import com.feelow.Feelow.jwt.MemberInfo;
import com.feelow.Feelow.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
public class MemberController {

    @Autowired
    private TokenProvider tokenProvider;

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
