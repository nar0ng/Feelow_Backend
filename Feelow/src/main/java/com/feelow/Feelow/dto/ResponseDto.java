package com.feelow.Feelow.dto;

import com.feelow.Feelow.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "set")
public class ResponseDto<D> {

    // 성공 / 실패 여부
    private boolean result;

    // 결과 메세지
    private String message;

    // 반환 데이터
    private D data;

    // 성공
    public static <D> ResponseDto<D> setSuccess(String message,D data){
        return  ResponseDto.set(true, message, data);
    }

    // 실패
    public static <D> ResponseDto<D> setFailed(String message,D data){
        return  ResponseDto.set(false, message, data);
    }
}