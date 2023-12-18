package com.feelow.Feelow.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    // Refresh token expiration time: 1 week
    private static final long REFRESH_TOKEN_EXPIRATION = 604800000; // 1주일

    // jwt 생성 및 검증을 위한 키 생성
    private  static final String SECURITY_KEY = "jwtseckey!@";


    // jwt 생성하는 메서드
    public String create (String email, String nickname){
        // 만료 기한 : 현재 시간 + 1시간
        Date exprTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        // jwt 생성
        return Jwts.builder()
                // 암호화에 사용될 알고리즘과 키
                .signWith(SignatureAlgorithm.HS512, SECURITY_KEY)
                // jwt 제목, 생성일, 만료일
                .setSubject(email)
                .claim("nickname", nickname)
                .setIssuedAt(new Date())
                .setExpiration(exprTime)
                .compact();
    }

    // jwt 검증
    public String validate(String token) {
        try{
        // 매개변수로 받은 token을 디코딩
        Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();

        // 추가 검증
        validateExpiration(claims);

        // 유효한 경우 subject 반환
        return claims.getSubject();
        } catch (Exception e) {
            // 검증 실패 시 null 반환 또는 예외 처리
            throw new BadCredentialsException("JWT validation failed", e);
        }
    }

    public MemberInfo getMemberInfo(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
        String email = claims.getSubject();
        String nickname = claims.get("nickname", String.class); 
        return new MemberInfo(email, nickname);
    }

    // Refresh token
    public String refresh(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();

            // 유효성 확인
            validateExpiration(claims);

            // 새로운 만료 시간 계산
            Date newExpiriationTime = Date.from(Instant.now().plus(REFRESH_TOKEN_EXPIRATION, ChronoUnit.MILLIS));

            // 리프레시 토큰 생성
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(newExpiriationTime)
                    .signWith(SignatureAlgorithm.HS512, SECURITY_KEY)
                    .compact();
        } catch (Exception e) {
            // 토큰 갱신 실패 시 예외 처리
            log.error("Token refresh failed", e);
            throw new BadCredentialsException("Token refresh failed", e);
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    private void validateExpiration(Claims claims) {
        // 토큰의 만료 시간 가져오기
        Date expiration = claims.getExpiration();
        // 현재 시간과 비교하여 만료 여부 확인
        if (expiration != null && expiration.before(new Date())) {
            throw new BadCredentialsException("JWT has expired");
        }
    }
    }


