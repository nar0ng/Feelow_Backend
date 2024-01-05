package com.feelow.Feelow.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberTypeUpdateDto {

    private Long id;
    private String member_type;


    public String getMemberType() {
        return member_type;
    }
}
