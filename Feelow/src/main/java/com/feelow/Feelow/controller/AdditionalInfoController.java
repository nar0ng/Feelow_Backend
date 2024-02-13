package com.feelow.Feelow.controller;

import com.feelow.Feelow.domain.dto.AdditionalInfoRequestDto;
import com.feelow.Feelow.domain.dto.MemberTypeUpdateDto;
import com.feelow.Feelow.domain.dto.ResponseDto;
import com.feelow.Feelow.service.AdditionalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class AdditionalInfoController {

    private final AdditionalInfoService additionalInfoService;

    @Autowired
    public AdditionalInfoController(AdditionalInfoService additionalInfoService) {
        this.additionalInfoService = additionalInfoService;
    }

    @PutMapping("/member-type/{memberId}")
    public ResponseEntity<ResponseDto<?>> updateMemberType(
            @PathVariable("memberId") Long memberId,
            @RequestBody MemberTypeUpdateDto memberTypeUpdateDto
    ) {
        try {
            ResponseDto<?> updateMemberTypeResponse = additionalInfoService.updateMemberType(memberId, memberTypeUpdateDto.getMemberType());
            return new ResponseEntity<>(updateMemberTypeResponse, HttpStatus.valueOf(updateMemberTypeResponse.getStatusCode()));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(ResponseDto.failed(HttpStatus.NOT_FOUND, "해당 ID의 회원을 찾을 수 없습니다.", null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseDto.failed(HttpStatus.BAD_GATEWAY, "Error", null), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/additional-info/{memberId}")
    public ResponseEntity<ResponseDto<?>> addAdditionalInfo(
            @PathVariable("memberId") Long memberId,
            @RequestBody AdditionalInfoRequestDto additionalInfoRequestDto
    ) {
        try {
            ResponseDto<?> addAdditionalInfoResponse = additionalInfoService.addAdditionalInfo(memberId, additionalInfoRequestDto);
            return new ResponseEntity<>(addAdditionalInfoResponse, HttpStatus.valueOf(addAdditionalInfoResponse.getStatusCode()));
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseDto.failed(HttpStatus.INTERNAL_SERVER_ERROR, "Additional 정보 추가에 실패했습니다.", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
