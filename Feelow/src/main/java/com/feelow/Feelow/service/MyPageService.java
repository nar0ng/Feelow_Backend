package com.feelow.Feelow.service;

import com.feelow.Feelow.domain.Student;
import com.feelow.Feelow.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MyPageService {

    private final StudentRepository studentRepository;

    public Optional<Student> getStudentById(Long studentId){
        return studentRepository.findByStudentId(studentId);
    }


}
