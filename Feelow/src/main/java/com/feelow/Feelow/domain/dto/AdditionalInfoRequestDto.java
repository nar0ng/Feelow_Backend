package com.feelow.Feelow.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalInfoRequestDto {

    private String school;
    private int grade;
    private int class_num;

    private String nickname;
    private int student_number;
    private String name;

    private MultipartFile file;

}
