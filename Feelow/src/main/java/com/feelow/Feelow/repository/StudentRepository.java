package com.feelow.Feelow.repository;

import com.feelow.Feelow.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
