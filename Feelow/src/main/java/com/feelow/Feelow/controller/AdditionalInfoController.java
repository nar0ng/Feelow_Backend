package com.feelow.Feelow.controller;

import com.feelow.Feelow.dto.MemberTypeUpdateDto;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.service.AdditionalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
public class AdditionalInfoController {

    private final AdditionalInfoService additionalInfoService;

    @Autowired
    public AdditionalInfoController(AdditionalInfoService additionalInfoService) {
        this.additionalInfoService = additionalInfoService;
    }

    @PutMapping("/{id}/member-type")
    public ResponseEntity<ResponseDto<?>> updateMemberType(
            @PathVariable("id") Long id,
            @RequestBody MemberTypeUpdateDto memberTypeUpdateDto
    ) {
        try {
            ResponseDto<?> updateMemberTypeResponse = additionalInfoService.updateMemberType(id, memberTypeUpdateDto.getMemberType());
            return new ResponseEntity<>(updateMemberTypeResponse, HttpStatus.valueOf(updateMemberTypeResponse.getStatusCode()));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(ResponseDto.failed(HttpStatus.NOT_FOUND, "해당 ID의 회원을 찾을 수 없습니다.", null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseDto.failed(HttpStatus.BAD_GATEWAY, "Error", null), HttpStatus.BAD_GATEWAY);
        }
    }
}
