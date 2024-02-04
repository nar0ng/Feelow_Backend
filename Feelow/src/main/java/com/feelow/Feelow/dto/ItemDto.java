package com.feelow.Feelow.dto;

import com.feelow.Feelow.entity.Item;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

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
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public Item toEntity() {return new Item(itemId, name, typeId, intro, price, createdAt, updateAt);}

}
