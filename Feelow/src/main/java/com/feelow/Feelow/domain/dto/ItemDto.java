package com.feelow.Feelow.domain.dto;

import com.feelow.Feelow.domain.embedded.Money;
import com.feelow.Feelow.domain.entity.Item;

import java.time.LocalDateTime;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ItemDto {

    private Long itemId; // 아이템 대표키
    private String name; // 아이템 이름
    private Long typeId; // ItemType 외래키
    private String intro; // 아이템 소개
    //private Money price; // 아이템 가격
    private Long price;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public Item toEntity() {return new Item(itemId, name, typeId, intro, price, createdAt, updateAt);}

}
