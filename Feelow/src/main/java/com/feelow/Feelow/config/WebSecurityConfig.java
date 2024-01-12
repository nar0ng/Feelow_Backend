package com.feelow.Feelow.config;

import com.feelow.Feelow.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    JwtAuthenticationFilter jwtAuthencationFilter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws  Exception{
        httpSecurity
                // cors 정책
                .cors(Customizer.withDefaults())
                // csrf 대책 : token을 사용하기 때문에 csrf 비활성화
                .csrf((csrf) -> csrf.disable())
                // basic 인증 (Bearer token 인증 방법을 사용하기 때문에 비활성화)
                .httpBasic((httpBasic) -> httpBasic.disable())
                // 세션 기반 인증
                .sessionManagement((sessionManagement) ->            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers("/", "/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll().anyRequest().authenticated());

        httpSecurity.addFilterBefore(jwtAuthencationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
