package com.feelow.Feelow.dto;

import com.feelow.Feelow.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto {
    private String token;
    private int exprTime;
    private Member member;

    // 여기에 ResponseDto의 setMember 메소드와 동일한 형태로 메소드 추가
    public static <D> ResponseDto<D> setMember(boolean result, String message, D data) {
        return ResponseDto.set(result, message, data);
    }
}
