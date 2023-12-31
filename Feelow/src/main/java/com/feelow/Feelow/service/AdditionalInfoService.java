package com.feelow.Feelow.service;

import com.feelow.Feelow.domain.Member;
import com.feelow.Feelow.dto.AdditionalInfoRequestDto;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.repository.MemberRepository;
import com.feelow.Feelow.repository.StudentRepository;
import com.feelow.Feelow.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdditionalInfoService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public ResponseDto<?> updateMemberType(Long id, String member_type) {
        try {
            Optional<Member> optionalMember = memberRepository.findById(id);

            if (optionalMember.isPresent()) {
                Member loginMember = optionalMember.get();
                loginMember.setMember_type(member_type);
                return ResponseDto.success("멤버 타입이 업데이트 되었습니다.", loginMember);
            } else {
                // 회원을 찾지 못한 경우에 대한 응답 반환
                return ResponseDto.failed(HttpStatus.NOT_FOUND, "해당 ID의 회원을 찾을 수 없습니다.", null);
            }
        } catch (Exception e) {
            // 로그에 예외 메시지 출력
            e.printStackTrace();
            // 예외 메시지를 포함한 실패 응답 반환
            return ResponseDto.failed(HttpStatus.INTERNAL_SERVER_ERROR, "멤버타입 업데이트에 실패했습니다: " + e.getMessage(), null);
        }
    }


}
