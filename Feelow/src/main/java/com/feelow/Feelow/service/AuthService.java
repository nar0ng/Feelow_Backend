package com.feelow.Feelow.service;

import com.feelow.Feelow.domain.Member;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.dto.SignInDto;
import com.feelow.Feelow.dto.SignInResponseDto;
import com.feelow.Feelow.dto.SignUpDto;
import com.feelow.Feelow.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    MemberRepository memberRepository;

    public ResponseDto<SignInResponseDto> signUpOrSignIn(SignUpDto dto) {
        Long id = dto.getId();

        try {
            // 동일한 id를 가진 회원이 이미 존재하는지 확인
            Optional<Member> existingMemberOptional = memberRepository.findById(id);

            if (existingMemberOptional.isPresent()) {
                // 이미 가입되어 있다면 해당 회원 정보를 반환
                Member existingMember = existingMemberOptional.get();

                String token = ""; // 필요한 경우 토큰을 생성 또는 검색
                int exprTime = 3600000;

                SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime, existingMember);
                return ResponseDto.setSuccess("로그인 성공", signInResponseDto);
            } else {
                // 가입이 안 되어 있다면 회원가입 처리
                Member newMember = new Member(dto);
                memberRepository.save(newMember);

                String token = ""; // 필요한 경우 토큰을 생성 또는 검색
                int exprTime = 3600000;

                SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime, newMember);
                return ResponseDto.setSuccess("가입 및 로그인 성공", signInResponseDto);
            }
        } catch (DataIntegrityViolationException e) {
            // 중복된 id로 인한 오류 처리
            return ResponseDto.setFailed("가입 실패: 이미 존재하는 ID입니다.", null);
        }
    }

    public ResponseDto<SignInResponseDto> signIn(SignInDto dto) {
        Long id = dto.getId();

        Optional<Member> existingMemberOptional = memberRepository.findById(id);

        if (existingMemberOptional.isPresent()) {
            // 이미 가입되어 있다면 해당 회원 정보를 반환
            Member existingMember = existingMemberOptional.get();

            String token = ""; // 필요한 경우 토큰을 생성 또는 검색
            int exprTime = 3600000;

            SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime, existingMember);
            return ResponseDto.setSuccess("로그인 성공", signInResponseDto);
        } else {
            return ResponseDto.setFailed("로그인 실패: 해당 ID로 가입된 회원이 없습니다.", null);
        }
    }
}