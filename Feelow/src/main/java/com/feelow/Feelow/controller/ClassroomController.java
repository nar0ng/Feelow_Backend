package com.feelow.Feelow.controller;

import com.feelow.Feelow.dto.ClassroomDto;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.service.ClassroomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/main/{memberId}")
@AllArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping()
    public ResponseDto<ClassroomDto> getStudentList(@PathVariable Long memberId) {
        return classroomService.findClassroomAndStudents(memberId);
    }
}