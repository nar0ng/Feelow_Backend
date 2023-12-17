package com.feelow.Feelow.controller;

import com.feelow.Feelow.domain.Member;
import com.feelow.Feelow.dto.MemberDto;
import com.feelow.Feelow.repository.MemberRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class MemberController {

    @GetMapping("/")
    public String getMember(@AuthenticationPrincipal MemberDto memberDto){

        return "안녕하세요!";
    }
}
