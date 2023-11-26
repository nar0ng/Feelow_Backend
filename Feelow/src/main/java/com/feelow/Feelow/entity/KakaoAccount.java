package com.feelow.Feelow.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;

@Embeddable
public class KakaoAccount {
    @JsonProperty("email")
    private String email;

}
