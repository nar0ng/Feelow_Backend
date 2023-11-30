package com.feelow.Feelow.service;

import com.feelow.Feelow.dto.SignUpDto;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.domain.Member;
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
        String email = dto.getEmail();

        // id 중복 확인
        try {
            if (memberRepository.existsById(id)) {
                // ID exists, return existing member
                Member existingMember = memberRepository.findById(id).orElse(null);
                if (existingMember != null) {
                    return ResponseDto.setSuccess("Already existing member", existingMember);
                }
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("Error", null);
        }

        // Member Entity 생성
        Member member = new Member(dto);
        // MemberRepository 이용해서 데이터베이스에 Entity 저장
        memberRepository.save(member);

        return ResponseDto.setSuccess("Sign up Success", member);
    }
}
