package com.feelow.Feelow.filter;

import com.feelow.Feelow.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Request가 들어왔을 때 Request Header의 Authorization 필드에 Bearer Token을 가져옴
    // 가져온 토큰을 검증하고 검증 결과를 SecurityContext에 추가

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected  void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String token = parseBearerToken(request);

        try {
            AbstractAuthenticationToken authentication = null;
            if (token != null && !token.equalsIgnoreCase("null")) {
                // 토큰 검증해서 payload의 email 가져옴
                String email = tokenProvider.validate(token);


                // SecurityContext에 추가할 객체
                authentication = new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.NO_AUTHORITIES);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            }
            // SecurityContext에 AbstractAuthenticationToken 객체를 추가해서
            // 해당 Thread가 지속적으로 인증 정보를 가질 수 있도록 해줌
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
        } catch (Exception exception) {
            // 예외가 발생하면 트랜잭션 롤백
            if (TransactionSynchronizationManager.isActualTransactionActive()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            exception.printStackTrace();
        }

        filterChain.doFilter(request, response);

    }


    // Request Header의 Authorization 필드의 Bearer Token을 가져오는 메서드
    private String parseBearerToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
            return null;

    }

}
