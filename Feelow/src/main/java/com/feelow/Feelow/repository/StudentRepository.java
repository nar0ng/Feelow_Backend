package com.feelow.Feelow.repository;

import com.feelow.Feelow.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(Long studentId);

    Optional<Student> findByMember_memberId(Long memberId);

    // Optional<Student> findById(Long id);

    List<Student> findByClassroomClassroomId(Long classroomId);
}
