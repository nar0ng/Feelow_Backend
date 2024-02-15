package com.feelow.Feelow.domain.embedded;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Embeddable
@Getter
public class OrderNumber implements Serializable {

    @Column(name = "order_number")
    private String number;

    private OrderNumber(String number){
        this.number = number;
    }

    public static OrderNumber of (String value){ // 객체 반환 메서드
        return new OrderNumber(value);
    }

    public static OrderNumber generateOrderNumber(){
        return new OrderNumber(generateRandomNumber(8));
    }

    public static String generateRandomNumber(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 10);
            stringBuilder.append(randomNum);
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals (Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        OrderNumber orderNo = (OrderNumber) o; // o 다운케스팅 하기
        return Objects.equals(number, orderNo.number);
    }

    @Override
    public int hashCode(){
        return Objects.hash(number);
    }

}
