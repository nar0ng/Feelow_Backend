package com.feelow.Feelow.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.feelow.Feelow.domain.dto.ItemTypeDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ItemType")
public class ItemType {

    @Id
    @JsonProperty("typeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId; // 1번 배게커버, 2번 악세서리, 3번 쿠폰

    @JsonProperty("title")
    private String title;

    public static ItemType createItemType(ItemTypeDto itemtypeDto){
        if(itemtypeDto.getTypeId() != null)
            throw new IllegalArgumentException("생성 실패! id가 없어야 합니다.");
        return new ItemType(
                itemtypeDto.getTypeId(),
                itemtypeDto.getTitle()
        );

    }

}
