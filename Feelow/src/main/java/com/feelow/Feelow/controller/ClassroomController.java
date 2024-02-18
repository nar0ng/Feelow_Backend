package com.feelow.Feelow.controller;

import com.feelow.Feelow.domain.dto.ClassroomDto;
import com.feelow.Feelow.domain.dto.ResponseDto;
import com.feelow.Feelow.service.ClassroomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/main/{memberId}")
@AllArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping()
    public ResponseDto<ClassroomDto> getStudentList(@PathVariable Long memberId) {
        boolean isApproved = classroomService.isApproved(memberId);
        // 승인 상태에 따라 응답 생성
        if (isApproved) {
            return classroomService.findClassroomAndStudents(memberId);
        } else {
            return ResponseDto.failed(HttpStatus.UNAUTHORIZED, "Access denied. Teacher's approval status is not approved.", null);
        }
    }
}