package com.feelow.Feelow.controller;

import com.feelow.Feelow.domain.dto.AdditionalInfoRequestDto;
import com.feelow.Feelow.domain.dto.MemberTypeUpdateDto;
import com.feelow.Feelow.domain.dto.ResponseDto;
import com.feelow.Feelow.service.AdditionalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/additional-info/{memberId}")
    public ResponseDto<?> addAdditionalInfo(
            @PathVariable("memberId") Long memberId,
            @RequestBody AdditionalInfoRequestDto additionalInfoRequestDto
    ) {
        try {
            ResponseDto<?> addAdditionalInfoResponse = additionalInfoService.addAdditionalInfo(memberId, additionalInfoRequestDto);
            return ResponseDto.success(HttpStatus.OK, "Additional info successfully", addAdditionalInfoResponse);
        } catch (Exception e) {
            return ResponseDto.failed(HttpStatus.INTERNAL_SERVER_ERROR, "Additional info failed", null);
        }
    }


}