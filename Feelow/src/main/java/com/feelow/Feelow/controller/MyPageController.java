package com.feelow.Feelow.controller;

import com.feelow.Feelow.domain.Member;
import com.feelow.Feelow.domain.Student;
import com.feelow.Feelow.domain.Teacher;
import com.feelow.Feelow.dto.MyPageDto;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.repository.MemberRepository;
import com.feelow.Feelow.repository.StudentRepository;
import com.feelow.Feelow.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MyPageController {

    private final MemberRepository memberRepository;

    @GetMapping("/{memberId}/mypage")
    public ResponseDto<MyPageDto> getMyPageInfo(@PathVariable Long memberId) {
        Optional<Member> getMember = memberRepository.findByMemberId(memberId);

        return getMember.map(member -> ResponseDto.success("MyPage info found", MyPageDto.fromMember(member)))
                .orElse(ResponseDto.failed("MyPage info not found", null));
    }


}
