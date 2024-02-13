package com.feelow.Feelow.controller;

import com.feelow.Feelow.domain.entity.Member;
import com.feelow.Feelow.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// 회원 정보를 받아서 db에 저장
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping
    public ResponseEntity<String> addMember(@RequestBody Member member) {
        memberRepository.save(member);
        return new ResponseEntity<>("Member added successfully!", HttpStatus.CREATED);
    }
}
