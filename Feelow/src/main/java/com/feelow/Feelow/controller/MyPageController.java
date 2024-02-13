package com.feelow.Feelow.controller;

import com.feelow.Feelow.domain.entity.Member;
import com.feelow.Feelow.domain.dto.MyPageDto;
import com.feelow.Feelow.domain.dto.ResponseDto;
import com.feelow.Feelow.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping
@AllArgsConstructor
public class MyPageController {

    private final MemberRepository memberRepository;

    @GetMapping("/api/mypage/{memberId}")
    public ResponseDto<MyPageDto> getMyPageInfo(@PathVariable Long memberId) {
        Optional<Member> getMember = memberRepository.findByMemberId(memberId);

        return getMember.map(member -> ResponseDto.success("MyPage info found", MyPageDto.fromMember(member)))
                .orElse(ResponseDto.failed("MyPage info not found", null));
    }


}
