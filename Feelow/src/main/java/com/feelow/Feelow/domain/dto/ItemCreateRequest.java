package com.feelow.Feelow.domain.dto;

import com.feelow.Feelow.domain.embedded.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemCreateRequest {
    private String name;
    private Money price;
}
