package com.feelow.Feelow.domain.embedded;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor
@Embeddable
@Getter
public class Money {

    private int value;

    public Money(int value){
        this.value = value;
    }

    public static Money of(Integer value){
        return new Money(value);
    }

}
