package com.feelow.Feelow.service;

import com.feelow.Feelow.domain.Member;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.dto.SignUpDto;
import com.feelow.Feelow.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    MemberRepository memberRepository;



    public ResponseDto<?> SignUp(SignUpDto dto){
        String email = dto.getEmail();
        Long id = dto.getId();

        // id 중복 확인
        try{
            if (memberRepository.existsById(id))
            {return ResponseDto.setFailed( "Exitsted Id!!", null);}
        } catch (Exception e){
            return ResponseDto.setFailed("error", null);
        }




        // Member Entity 생성
        Member member = new Member(dto);
        // MemberRepository 이용해서 데이터베이스에 Entity 저장
        memberRepository.save(member);

        return ResponseDto.setSuccess("Sign up Success", null);
    }


}
