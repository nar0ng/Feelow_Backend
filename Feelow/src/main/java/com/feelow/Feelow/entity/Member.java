package com.feelow.Feelow.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("connected_at")
    private LocalDateTime connected_at;

    @JsonProperty("properties")
    @Embedded
    private Properties properties;

    @JsonProperty("kakao_account")
    @Embedded
    private KakaoAccount kakaoAccount;
}


