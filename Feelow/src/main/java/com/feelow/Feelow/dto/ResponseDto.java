package com.feelow.Feelow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "set")
@NoArgsConstructor
public class ResponseDto<D> {
    private boolean result;
    private String message;
    private D data;

    public static <D> ResponseDto<D> setSuccess(String message,D data){
        return  ResponseDto.set(true, message, data);
    }

    public static <D> ResponseDto<D> setFailed(String message,D data){
        return  ResponseDto.set(false, message, data);
    }


}
