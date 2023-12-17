package com.feelow.Feelow.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;


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
                .compact();
    }

    // jwt 검증
    public String validate(String token) {
        // 매개변수로 받은 token을 디코딩
        Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
        // 복호화된 토큰의 payload에서 subject를 가져옴
        return claims.getSubject();
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
