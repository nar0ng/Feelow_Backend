package com.feelow.Feelow.dto;
import com.feelow.Feelow.entity.Item;
import com.feelow.Feelow.entity.ItemType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemTypeDto {
    private Long typeId;
    private String title;

    public ItemType toEntity() { return new ItemType(typeId, title); }
}
