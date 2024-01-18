package com.feelow.Feelow.controller;

import com.feelow.Feelow.domain.Student;
import com.feelow.Feelow.dto.MyPageDto;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/{studentId}")
@AllArgsConstructor
public class MyPageController {

    private final StudentRepository studentRepository;

    @GetMapping("/mypage")
    public ResponseDto<MyPageDto> getMyPageInfo(@PathVariable Long studentId) {
        Optional<Student> getStudent = studentRepository.findByStudentId(studentId);

        return getStudent.map(student -> ResponseDto.success("MyPage info found", MyPageDto.fromStudent(student)))
                .orElse(ResponseDto.failed("MyPage info not found", null));
    }
}
