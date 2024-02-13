package com.feelow.Feelow.service;

import com.feelow.Feelow.domain.dto.ItemTypeDto;
import com.feelow.Feelow.domain.entity.ItemType;
import com.feelow.Feelow.repository.ItemTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ItemTypeService {
    @Autowired
    private ItemTypeRepository itemTypeRepository;

    @Transactional
    public ItemType iupload(ItemTypeDto itemTypeDto){
        ItemType itemType = ItemType.createItemType(itemTypeDto);
        itemTypeRepository.save(itemType);
        return itemType;
    }

    public ItemType getItemType(Long itemId) {return itemTypeRepository.findByItemId(itemId); }
    public List<ItemType> getAllItemType() {return itemTypeRepository.findAll();}


}
