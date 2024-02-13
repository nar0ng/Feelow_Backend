package com.feelow.Feelow.domain.dto;

import com.feelow.Feelow.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private Long memberId;

    private Long id;

    private LocalDateTime connected_at;

    private String nickname;

    private String email;

    private String member_type;

    private Long studentId;

    private Long teacherId;

    public static MemberDto getMember(Member member) {
        if ("student".equals(member.getMemberType()) && member.getStudent() != null) {
            // member_type이 student이고 Student 정보가 있을 경우 studentId를 사용
            return MemberDto.builder()
                    .memberId(member.getMemberId())
                    .id(member.getId())
                    .connected_at(member.getConnected_at())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .member_type(member.getMemberType())
                    .studentId(member.getStudentId())
                    .build();
        } else if ("teacher".equals(member.getMemberType()) && member.getTeacher() != null) {
            System.out.println("teacher id");
            return MemberDto.builder()
                    .memberId(member.getMemberId())
                    .id(member.getId())
                    .connected_at(member.getConnected_at())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .member_type(member.getMemberType())
                    .teacherId(member.getTeacher().getTeacherId())
                    .build();
        } else {
            // 그 외의 경우에는 기존의 로직 사용
            return MemberDto.builder()
                    .memberId(member.getMemberId())
                    .id(member.getId())
                    .connected_at(member.getConnected_at())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .member_type(member.getMemberType())
                    .build();
        }
    }
}


