package com.feelow.Feelow.repository;

import com.feelow.Feelow.domain.Student;
import com.feelow.Feelow.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByMember_memberId(Long memberId);
}
