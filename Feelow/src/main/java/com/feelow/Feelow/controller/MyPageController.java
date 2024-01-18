package com.feelow.Feelow.controller;

import com.feelow.Feelow.domain.Student;
import com.feelow.Feelow.domain.Teacher;
import com.feelow.Feelow.dto.MyPageDto;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.repository.StudentRepository;
import com.feelow.Feelow.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MyPageController {

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    @GetMapping("/{studentId}/mypage/student")
    public ResponseDto<MyPageDto> getStudentMyPageInfo(@PathVariable Long studentId) {
        Optional<Student> getStudent = studentRepository.findByStudentId(studentId);

        return getStudent.map(student -> ResponseDto.success("MyPage info found", MyPageDto.fromStudent(student)))
                .orElse(ResponseDto.failed("MyPage info not found", null));
    }

    @GetMapping("/{teacherId}/mypage/teacher")
    public ResponseDto<MyPageDto> getTeacherMyPageInfo(@PathVariable Long teacherId) {
        Optional<Teacher> getTeacher = teacherRepository.findByTeacherId(teacherId);

        return getTeacher.map(teacher -> ResponseDto.success("MyPage info found", MyPageDto.fromTeacher(teacher)))
                .orElse(ResponseDto.failed("MyPage info not found", null));
    }
}
