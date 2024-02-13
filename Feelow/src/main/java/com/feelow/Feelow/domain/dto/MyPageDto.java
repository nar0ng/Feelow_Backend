package com.feelow.Feelow.domain.dto;

import com.feelow.Feelow.domain.entity.Classroom;
import com.feelow.Feelow.domain.entity.Member;
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

    private Long teacherId;
    private String teacherName;

    private String school;
    private int grade;
    private int classNum;

    public static MyPageDto fromMember(Member member){

        if ("student".equals(member.getMemberType()) && member.getTeacher() != null) {
            Classroom classroom = member.getStudent().getClassroom();

            return MyPageDto.builder()
                    .studentId(member.getStudentId())
                    .nickname(member.getStudent().getNickname())
                    .studentNumber(member.getStudent().getStudentNumber())
                    .studentName(member.getStudent().getStudentName())
                    .school(classroom.getSchool())
                    .grade(classroom.getGrade())
                    .classNum(classroom.getClassNum())
                    .build();

        } else if ("teacher".equals(member.getMemberType()) && member.getTeacher() != null) {
            Classroom classroom = member.getTeacher().getClassroom();

            return MyPageDto.builder()
                    .teacherId(member.getTeacher().getTeacherId())
                    .teacherName(member.getTeacher().getTeacherName())
                    .school(classroom.getSchool())
                    .grade(classroom.getGrade())
                    .classNum(classroom.getClassNum())
                    .build();
        }

        return null;
    }
}
