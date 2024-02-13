package com.feelow.Feelow.repository;

import com.feelow.Feelow.domain.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Optional<Classroom> findByClassroomId(Long classroomId);

    Optional<Classroom> findByTeacherTeacherId(Long teacherId);
    Optional<Classroom> findBySchoolAndGradeAndClassNum(String school, int grade, int class_num);
}
