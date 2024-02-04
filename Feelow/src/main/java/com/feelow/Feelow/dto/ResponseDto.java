package com.feelow.Feelow.dto;

import com.feelow.Feelow.domain.Chat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "set")
@NoArgsConstructor
@Builder
public class ResponseDto<D> {

    private boolean success;
    private int statusCode;
    private String message;
    private D data;


    public static <D> ResponseDto<D> success(String message, D data) {
        return ResponseDto.set(true, HttpStatus.OK.value(), message, data);
    }

    public static <D> ResponseDto<D> success(HttpStatus httpStatus, String message, D data) {
        return ResponseDto.set(true, httpStatus.value(), message, data);
    }

    public static <D> ResponseDto<D> failed(String message, D data) {
        return ResponseDto.set(false, HttpStatus.BAD_GATEWAY.value(), message, data);
    }

    public static <D> ResponseDto<D> failed(HttpStatus httpStatus, String message, D data) {
        return ResponseDto.set(false, httpStatus.value(), message, data);
    }

    public static ResponseDto<List<Chat>> successChat(String message, List<Chat> data) {
        return ResponseDto.set(true, HttpStatus.OK.value(), message, data);
    }

}