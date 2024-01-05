package com.feelow.Feelow.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "Item")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId", unique = true, nullable = false)
    private Long itemId;

    @Column(length =50, nullable = false)
    private String name;

    @Column(nullable = false)
    private Long typeId;

    @Column(length = 200, nullable = false)
    private String intro;

}
