package com.feelow.Feelow.repository;

import com.feelow.Feelow.domain.entity.Item;
import com.feelow.Feelow.domain.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>{

    Item findByItemId(Long itemId);
    List<Item> findByTypeId(Long typeId);

    // 쿼리??

}
