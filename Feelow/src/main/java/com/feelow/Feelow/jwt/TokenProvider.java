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

    // Refresh token expiration time: 1주일
    private static final long REFRESH_TOKEN_EXPIRATION = 604800000;

    // jwt 생성 및 검증을 위한 키 생성
    private  static final String SECURITY_KEY = "jwtseckey!@";

    // jwt 생성하는 메서드
    public String create (String email, String nickname){
        // 만료 기한 : 현재 시간 + 1시간
        Date exprTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        // 리프레시 토큰 만료 기한: 현재 시간 + 1주일
        Date refreshExprTime = Date.from(Instant.now().plus(REFRESH_TOKEN_EXPIRATION, ChronoUnit.MILLIS));

        // 리프레시 토큰 생성
        String refreshToken = Jwts.builder()
                .setSubject(email)
                .setExpiration(refreshExprTime)
                .signWith(SignatureAlgorithm.HS512, SECURITY_KEY)
                .compact();

        // jwt 생성
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECURITY_KEY)
                .setSubject(email)
                .claim("nickname", nickname)
                .setIssuedAt(new Date())
                .setExpiration(exprTime)
                .claim("refreshToken", refreshToken)  // 리프레시 토큰을 클레임에 추가
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
    public String refresh(String accessToken){
        try {
            Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(accessToken).getBody();

            // 유효성 확인
            validateExpiration(claims);

            // 리프레시 토큰 갱신
            Date newExpirationTime = Date.from(Instant.now().plus(REFRESH_TOKEN_EXPIRATION, ChronoUnit.MILLIS));
            String refreshToken = claims.get("refreshToken", String.class);

            // 리프레시 토큰이 존재하면 기존 리프레시 토큰을 사용하여 갱신
            if (refreshToken != null) {
                return Jwts.builder()
                        .setClaims(claims)
                        .setExpiration(newExpirationTime)
                        .signWith(SignatureAlgorithm.HS512, SECURITY_KEY)
                        .compact();
            } else {
                throw new RuntimeException("No existing refreshToken found for refresh");
            }
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
    // TokenProvider 클래스 내에 추가할 메서드
    public boolean isAccessTokenExpired(String accessToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(accessToken).getBody();
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true; // 만료 여부 확인 중 에러 발생 시도 만료된 것으로 처리
        }
    }

}



