package com.feelow.Feelow.controller;

import com.feelow.Feelow.dto.ItemDto;
import com.feelow.Feelow.entity.Item;
import com.feelow.Feelow.entity.ItemType;
import com.feelow.Feelow.repository.ItemTypeRepository;
import com.feelow.Feelow.service.ItemTypeService;
import com.feelow.Feelow.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemTypeRepository itemTypeRepository;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadItem(@RequestBody final ItemDto itemDto){
        itemService.uploadItem(itemDto);
        return ResponseEntity.status(HttpStatus.OK).body("아이템 업로드 완료");
    }

    @GetMapping("/list")
    public ResponseEntity<List<ItemDto>> getItemList(){
        List<ItemDto> itemDtoList = itemService.getItemListDto();
        return new ResponseEntity<>(itemDtoList, HttpStatus.OK);
    }

    @GetMapping("/list/{typeId}")
    public ResponseEntity<List<ItemDto>> getItemListByType(@PathVariable Long typeId){
        List<ItemDto> ItemDtoList = itemService.getItemListByType(typeId);
        return new ResponseEntity<>(ItemDtoList, HttpStatus.OK);
    }
    


}
