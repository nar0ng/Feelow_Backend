package com.feelow.Feelow.dto;

import com.feelow.Feelow.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {

    // 제공 토큰
    private String accessToken;

    // 리프레쉬 토큰
    private String refreshToken;

    // 만료 시간
    private int exprTime;
    private MemberDto member;
}