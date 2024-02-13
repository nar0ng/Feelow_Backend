package com.feelow.Feelow.repository;

import com.feelow.Feelow.domain.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {

    ItemType findByTypeId(Long typeId);

    @Query(value = "SELECT * FROM ItemType WHERE type_id = (SELECT type_id FROM Item WEHRE item_id=:itemId)", nativeQuery = true)
    ItemType findByItemId(@Param("itemId") Long itemId);

}
