package com.feelow.Feelow.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.feelow.Feelow.dto.SignUpDto;
import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Member")
@Table(name="Member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("connected_at")
    private LocalDateTime connected_at;

    private String nickname;

    private String email;

    private String memberType;

    @OneToOne(mappedBy = "member")
    private Teacher teacher;

    @OneToOne(mappedBy = "member")
    private Student student;

    @SuppressWarnings("unchecked")
    @JsonProperty("properties")
    private void unpackNested_p(Map<String,Object> properties) {
        this.nickname = (String)properties.get("nickname");
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("kakao_account")
    private void unpackNested_k(Map<String,Object> kakao_account) {
        this.email = (String)kakao_account.get("email");
    }

    // SignUpDto를 사용하여 Member 객체 생성
    public Member(SignUpDto dto){
        this.id = dto.getId();
        this.connected_at = dto.getConnected_at();
        this.email = dto.getEmail();
        this.nickname = dto.getNickname();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id.equals(member.id);
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Chat> chats;

    @Override
    public int hashCode() {
        return id.hashCode();
    }



    public Long getStudentId() {
        return (student != null) ? student.getStudentId() : null;
    }
}

