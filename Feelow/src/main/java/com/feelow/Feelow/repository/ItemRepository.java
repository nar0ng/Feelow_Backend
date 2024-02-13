package com.feelow.Feelow.repository;

import com.feelow.Feelow.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>{

    Item findByItemId(Long itemId);
    List<Item> findByTypeId(Long typeId);

}
