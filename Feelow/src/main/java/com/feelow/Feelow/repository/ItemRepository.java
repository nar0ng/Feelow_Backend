package com.feelow.Feelow.repository;

import com.feelow.Feelow.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>{

    Item findByItemId(Long itemId);
    List<Item> findByTypeId(Long typeId);

}
