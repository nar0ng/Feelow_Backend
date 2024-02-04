package com.feelow.Feelow.service;


import com.feelow.Feelow.entity.Item;
import com.feelow.Feelow.dto.ItemDto;
import com.feelow.Feelow.repository.ItemRepository;
import com.feelow.Feelow.repository.ItemTypeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemTypeRepository itemTypeRepository;


    public void uploadItem(ItemDto itemDto){
        Item item = convertDtoToEntity(itemDto);
        itemRepository.save(item);
    }

    public List<ItemDto> getItemListDto() {
        List<Item> itemList = itemRepository.findAll();
        return itemList.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

    }

    public List<ItemDto> getItemListByType(Long typeId){
        List<Item> itemList = itemRepository.findByTypeId(typeId);
        return itemList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
        //return itemList.stream()
        //        .map(this::convertEntityToDto)
        //        .collect(Collectors.toList());

    }

    private Item convertDtoToEntity(ItemDto itemDto){
        return Item.builder()
                .name(itemDto.getName())
                .typeId(itemDto.getTypeId())
                .intro(itemDto.getIntro())
                .build();
    }

    private ItemDto convertEntityToDto(Item item){
        return ItemDto.builder()
                .itemId(item.getItemId())
                .name(item.getName())
                .typeId(item.getTypeId())
                .intro(item.getIntro())
                .build();
    }


}
