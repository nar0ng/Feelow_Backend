package com.feelow.Feelow.dto;

import com.feelow.Feelow.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor(staticName = "set")
public class ResponseDto<D> {

    // 성공 / 실패 여부
    private boolean result;

    // 상태 코드
    private int statusCode;

    // 결과 메세지
    private String message;

    // 반환 데이터
    private D data;

    // 성공
    public static <D> ResponseDto<D> setSuccess(HttpStatus httpStatus, String message, D data){
        return  ResponseDto.set(true, httpStatus.value(), message, data);
    }

    // 실패
    public static <D> ResponseDto<D> setFailed(HttpStatus httpStatus, String message,D data){
        return  ResponseDto.set(false, httpStatus.value(), message, data);
    }
}