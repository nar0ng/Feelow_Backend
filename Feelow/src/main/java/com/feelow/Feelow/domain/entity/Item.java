package com.feelow.Feelow.domain.entity;

import com.feelow.Feelow.domain.dto.ItemCreateRequest;
import com.feelow.Feelow.domain.embedded.Money;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonProperty;

@Builder
@Table(name = "Item")
@Entity(name = "Item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Item {

    @Id
    @JsonProperty("itemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("typeId")
    private Long typeId;

    @JsonProperty("intro")
    private String intro;

    @Column(nullable = false)
    @JsonProperty("price")
    private Money price;

    @CreationTimestamp
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonProperty("updated_at")
    private LocalDateTime updateAt;


    public Item(String name, Money price) {
        this.name = name;
        this.price = price;
    }


    // 아이템 생성 요청을 받으면 아이템 객체를 생성해서 반환
    public static Item of(ItemCreateRequest request){
        return new Item(request.getName(), request.getPrice());
    }

    public Money calculate(int quantity){
        return Money.of(price.getValue()*quantity);
    }


}
