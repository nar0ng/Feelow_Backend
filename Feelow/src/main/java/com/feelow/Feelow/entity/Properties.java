package com.feelow.Feelow.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;

@Embeddable
public class Properties {
    @JsonProperty("nickname")
    private String nickname;

}
