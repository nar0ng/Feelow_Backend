package com.feelow.Feelow.service;

import com.feelow.Feelow.dto.SignUpDto;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.domain.Member;
import com.feelow.Feelow.dto.MemberResponseDto;
import com.feelow.Feelow.jwt.TokenProvider;
import com.feelow.Feelow.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TokenProvider tokenProvider;

    public ResponseDto<?> signUp(SignUpDto dto) {
        Long id = dto.getId();
        String email = dto.getEmail();
        String nickname = dto.getNickname();

        try {
            // 이미 존재하는 ID인지 확인
            Optional<Member> existingMemberOptional = memberRepository.findById(id);

            if (existingMemberOptional.isPresent()) {

                // ID가 이미 존재하면, 기존 회원 정보를 반환
                Member existingMember = existingMemberOptional.get();

                // 토큰 생성
                String accessToken = tokenProvider.create(email, nickname);
                String refreshToken = tokenProvider.refresh(accessToken); // refreshToken 생성
                int exprTime = 3600000; // 1시간


                // 만료된 accessToken인 경우 refreshToken을 사용하여 새로운 accessToken 발급
                if (tokenProvider.isAccessTokenExpired(accessToken)) {
                    accessToken = tokenProvider.refresh(refreshToken);
                }

                // 갱신된 토큰 및 만료 시간을 응답 DTO에 담아 반환
                MemberResponseDto memberResponseDto = new MemberResponseDto(accessToken, refreshToken, exprTime, existingMember);
                return ResponseDto.success(HttpStatus.OK, "Already existing member", memberResponseDto);
            } else {

                /// 존재하지 않는 ID인 경우, Member 엔티티 생성 및 저장
                Member newMember = new Member(dto);
                memberRepository.save(newMember);

                // 토큰 생성
                String accessToken = tokenProvider.create(email, nickname);
                String refreshToken = tokenProvider.refresh(accessToken); // refreshToken 생성
                int exprTime = 3600000; // 1시간
                //int exprTime = 300000; // 5분 (test)


                // 만료된 accessToken인 경우 refreshToken을 사용하여 새로운 accessToken 발급
                if (tokenProvider.isAccessTokenExpired(accessToken)) {
                    accessToken = tokenProvider.refresh(refreshToken);
                }

                MemberResponseDto memberResponseDto = new MemberResponseDto(accessToken, refreshToken, exprTime, newMember);
                return ResponseDto.success(HttpStatus.CREATED, "Sign up Success", memberResponseDto);
            }
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseDto.failed(HttpStatus.BAD_GATEWAY, "Error", null);
        }


    }
}
