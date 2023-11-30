package com.feelow.Feelow.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.feelow.Feelow.dto.SignUpDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Member")
@Table(name="Member")
public class Member {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long member_id;

    @Id
    @JsonProperty("id")
    @Column(name = "id")
    private Long id;

    @JsonProperty("connected_at")
    private LocalDateTime connected_at;

    private String nickname;

    private String email;

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

    public Member(SignUpDto dto){
        this.member_id = dto.getMember_id();
        this.id = dto.getId();
        this.connected_at = dto.getConnected_at();
        this.email = dto.getEmail();
        this.nickname = dto.getNickname();
    }

    // equals 및 hashCode 추가
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}

