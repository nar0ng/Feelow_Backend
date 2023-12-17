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

                String token = tokenProvider.create(email, nickname);
                int exprTime = 3600000;

                // ID가 이미 존재하면, 기존 회원 정보를 반환
                Member existingMember = existingMemberOptional.get();
                MemberResponseDto memberResponseDto = new MemberResponseDto(token, exprTime, existingMember);
                return ResponseDto.success(HttpStatus.OK, "Already existing member", memberResponseDto);
            } else {

                String token = tokenProvider.create(email, nickname);
                int exprTime = 3600000;

                /// 존재하지 않는 ID인 경우, Member 엔티티 생성 및 저장
                Member newMember = new Member(dto);
                memberRepository.save(newMember);

                MemberResponseDto memberResponseDto = new MemberResponseDto(token, exprTime, newMember);
                return ResponseDto.success(HttpStatus.CREATED, "Sign up Success", memberResponseDto);
            }
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseDto.failed(HttpStatus.BAD_GATEWAY, "Error", null);
        }


    }
}
