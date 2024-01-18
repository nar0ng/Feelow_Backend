package com.feelow.Feelow.dto;

import com.feelow.Feelow.domain.Classroom;
import com.feelow.Feelow.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPageDto {

    private Long studentId;
    private String nickname;
    private int studentNumber;
    private String studentName;

    private String school;
    private int grade;
    private int classNum;

    public static MyPageDto fromStudent(Student student) {
        Classroom classroom = student.getClassroom();

        return MyPageDto.builder()
                .studentId(student.getStudentId())
                .nickname(student.getNickname())
                .studentNumber(student.getStudentNumber())
                .studentName(student.getStudentName())
                .school(classroom.getSchool())
                .grade(classroom.getGrade())
                .classNum(classroom.getClassNum())
                .build();
    }
}
