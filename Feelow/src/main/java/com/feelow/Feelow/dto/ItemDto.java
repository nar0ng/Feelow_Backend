package com.feelow.Feelow.dto;

import com.feelow.Feelow.entity.Item;
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
    private Long price; // 아이템 가격

    public Item toEntity() {return new Item(itemId, name, typeId, intro, price);}

}
