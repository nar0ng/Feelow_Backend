package com.feelow.Feelow.service;

import com.feelow.Feelow.dto.SignUpDto;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.domain.Member;
import com.feelow.Feelow.dto.SignInResponseDto;
import com.feelow.Feelow.jwt.TokenProvider;
import com.feelow.Feelow.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        String token = tokenProvider.create(email);
        int exprTime = 3600000;


        try {
            // 이미 존재하는 ID인지 확인
            Optional<Member> existingMemberOptional = memberRepository.findById(id);

            if (existingMemberOptional.isPresent()) {
                // ID가 이미 존재하면, 기존 회원 정보를 반환
                Member existingMember = existingMemberOptional.get();
                SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime, existingMember);  // Token과 exprTime은 null 및 0으로 초기화
                return ResponseDto.setSuccess("Already existing member", signInResponseDto);
            } else {
                /// 존재하지 않는 ID인 경우, Member 엔티티 생성 및 저장
                 Member member = new Member(dto);
                memberRepository.save(member);

                // 회원 정보를 담아 ResponseDto 반환
                return ResponseDto.setSuccess("Sign up Success", member);
            }
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseDto.setFailed("Error", null);
        }


    }
}
