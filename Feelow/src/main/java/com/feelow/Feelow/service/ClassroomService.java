package com.feelow.Feelow.service;

import com.feelow.Feelow.domain.entity.*;
import com.feelow.Feelow.domain.dto.ClassroomDto;
import com.feelow.Feelow.domain.dto.ResponseDto;
import com.feelow.Feelow.repository.ClassroomRepository;
import com.feelow.Feelow.repository.MemberRepository;
import com.feelow.Feelow.repository.StudentRepository;
import com.feelow.Feelow.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    private final MemberRepository memberRepository;

    private final TeacherRepository teacherRepository;

    private final StudentRepository studentRepository;

    public ResponseDto<ClassroomDto> findClassroomAndStudents(Long memberId){
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);

        if (optionalMember.isPresent()){
            if("teacher".equals(optionalMember.get().getMemberType())) {
                Optional<Teacher> optionalTeacher = teacherRepository.findByMember_memberId(memberId);

                if (optionalTeacher.isPresent()) {
                    Optional<Classroom> optionalClassroom = classroomRepository.findByTeacherTeacherId(optionalTeacher.get().getTeacherId());

                    if (optionalClassroom.isPresent()) {
                        Classroom classroom = optionalClassroom.get();
                        List<Student> studentList = studentRepository.findByClassroomClassroomId(classroom.getClassroomId());

                        ClassroomDto classroomDto = ClassroomDto.builder()
                                .classroomId(classroom.getClassroomId())
                                .school(classroom.getSchool())
                                .grade(classroom.getGrade())
                                .classNum(classroom.getClassNum())
                                .students(studentList)
                                .build();

                        return ResponseDto.success(HttpStatus.OK, "Classroom and Student List retrieved successfully", classroomDto);
                    } else {
                        return ResponseDto.failed(HttpStatus.NOT_FOUND, "Teacher's classroom not found", null);
                    }
                } else {
                    return ResponseDto.failed(HttpStatus.NOT_FOUND, "Member is not a teacher", null);
                }
            }else {
                return ResponseDto.failed(HttpStatus.NOT_FOUND, "Member not found", null);
            }
        } else {
            return ResponseDto.failed(HttpStatus.NOT_FOUND, "Member not found", null);
    }
    }

    public boolean isApproved(Long memberId){
        Member member = memberRepository.findByMemberId(memberId).orElse(null);

        if (member != null){
            ApprovalStatus approvalStatus = member.getTeacher().getApprovalStatus();

            switch (approvalStatus){
                case APPROVED:
                    return true;
                case PENDING:
                case REJECTED:
                default:
                    return false;
            }
        }
        return false; // member가 null인 경우에도 승인되지 않은 것으로 처리
    }

}
