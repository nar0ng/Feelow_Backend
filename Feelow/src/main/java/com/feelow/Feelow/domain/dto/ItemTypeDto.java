package com.feelow.Feelow.domain.dto;
import com.feelow.Feelow.domain.entity.ItemType;
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
