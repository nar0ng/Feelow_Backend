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
    @Column(name = "itemId", unique = true, nullable = false)
    private Long itemId;

    @JsonProperty("name")
    @Column(length =50, nullable = false)
    private String name;

    @JsonProperty("typeId")
    @Column(nullable = false)
    private Long typeId;

    @JsonProperty("intro")
    @Column(length = 200, nullable = false)
    private String intro;

    @JsonProperty("price")
    @Column(nullable = false)
    private Money price;

    @CreationTimestamp
    @JsonProperty("created_at")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonProperty("updated_at")
    @Column(name = "updated_at", nullable = false)
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
