package com.feelow.Feelow.domain.dto;

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

    private String nickname;

    private String email;

    public MemberResponseDto(MemberDto member){
        this.email = member.getEmail();
        this.nickname = member.getNickname();
    }


    // 만료 시간
    private int exprTime;
    private MemberDto member;
}