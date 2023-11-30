package com.feelow.Feelow.service;

import com.feelow.Feelow.dto.SignUpDto;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.domain.Member;
import com.feelow.Feelow.dto.SignInResponseDto;
import com.feelow.Feelow.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    MemberRepository memberRepository;

    public ResponseDto<?> signUp(SignUpDto dto) {
        Long id = dto.getId();
        System.out.println("ㅌㅌㅌㅌㅌㅌㅌㅌㅌㅌㅌ");

        try {
            Optional<Member> existingMemberOptional = memberRepository.findById(id);

            if (existingMemberOptional.isPresent()) {
                System.out.println("안");
                // ID exists, return existing member in SignInResponseDto
                Member existingMember = existingMemberOptional.get();
                SignInResponseDto signInResponseDto = new SignInResponseDto(null, 0, existingMember);  // Token과 exprTime은 null 및 0으로 초기화
                return ResponseDto.setSuccess("Already existing member", signInResponseDto);
            } else {
                // 로그를 통해 ID가 제대로 설정되어 있는지 확인
                System.out.println("ID: " + id);

                // Member Entity 생성 및 데이터베이스에 Entity 저장
                // Member member = new Member(dto);
               // memberRepository.save(member);

                // 회원 정보를 담아 ResponseDto 반환
                return ResponseDto.setSuccess("Sign up Success", null);
            }
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseDto.setFailed("Error", null);
        }

        // Handle unexpected situations
    }
}
