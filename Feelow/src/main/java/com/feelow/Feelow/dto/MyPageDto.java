package com.feelow.Feelow.dto;

import com.feelow.Feelow.domain.Classroom;
import com.feelow.Feelow.domain.Student;
import com.feelow.Feelow.domain.Teacher;
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

    public static MyPageDto fromTeacher(Teacher teacher) {
        Classroom classroom = teacher.getClassroom();

        return MyPageDto.builder()
                .teacherId(teacher.getTeacherId())
                .teacherName(teacher.getTeacherName())
                .school(classroom.getSchool())
                .grade(classroom.getGrade())
                .classNum(classroom.getClassNum())
                .build();
    }
}
