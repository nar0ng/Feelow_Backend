package com.feelow.Feelow.service;

import com.feelow.Feelow.domain.entity.Member;
import com.feelow.Feelow.domain.entity.Student;
import com.feelow.Feelow.repository.MemberRepository;
import com.feelow.Feelow.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MyPageService {

    private final StudentRepository studentRepository;

    private final MemberRepository memberRepository;

    public Optional<Student> getStudentById(Long studentId){
        return studentRepository.findByStudentId(studentId);
    }

    public Optional<Member> getMemberById(Long memberId){
        return memberRepository.findByMemberId(memberId);
    }


}
