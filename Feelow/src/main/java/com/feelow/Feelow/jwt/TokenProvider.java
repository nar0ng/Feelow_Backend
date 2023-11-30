package com.feelow.Feelow.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {
    // jwt 생성 및 검증을 위한 키 생성
    private  static final String SECURITY_KEY = "jwtseckey!@";

    // jwt 생성하는 메서드
    public String create (String email){
        // 만료 기한 : 현재 시간 + 1시간
        Date exprTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        // jwt 생성
        return Jwts.builder()
                // 암호화에 사용될 알고리즘과 키
                .signWith(SignatureAlgorithm.HS512, SECURITY_KEY)
                // jwt 제목, 생성일, 만료일
                .setSubject(email).setIssuedAt(new Date()).setExpiration(exprTime)
                // 생성
                .compact();
    }

    // jwt 검증
    public String validate(String token) {
        // 매개변수로 받은 token을 디코딩
        Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
        // 복호화된 토큰의 payload에서 subject를 가져옴
        return claims.getSubject();
    }
}
