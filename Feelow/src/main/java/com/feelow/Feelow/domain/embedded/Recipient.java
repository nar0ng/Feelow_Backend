package com.feelow.Feelow.domain.embedded;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
@Getter
public class Recipient {
    /*
    @Valid
    @Embedded
    private String name;


    @Valid
    @Embedded
    private Address address; // 필요없을거같은데??
    */
}
