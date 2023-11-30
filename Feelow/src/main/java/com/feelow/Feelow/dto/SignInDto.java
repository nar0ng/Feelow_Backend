package com.feelow.Feelow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("connected_at")
    private LocalDateTime connected_at;

    private String nickname;

    private String email;

    @JsonProperty("properties")
    private void unpackNested_p(Map<String,Object> properties) {
        this.nickname = (String)properties.get("nickname");
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("kakao_account")
    private void unpackNested_k(Map<String,Object> kakao_account) {
        this.email = (String)kakao_account.get("email");
    }


}
