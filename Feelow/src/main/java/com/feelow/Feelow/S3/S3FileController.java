package com.feelow.Feelow.S3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.feelow.Feelow.domain.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class S3FileController {
    private final AmazonS3Client amazonS3Client;
    private final S3ImageService s3ImageService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;


    @PostMapping("/upload/{memberId}")
    public ResponseDto<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("memberId") Long memberId) {
        try {
            String fileName = file.getOriginalFilename();
            String serveraddress = s3ImageService.getCurrentServerAddress();
            String fileUrl =  serveraddress + "api/images/" + bucketName + "/" + fileName;
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucketName,fileName,file.getInputStream(),metadata);
            s3ImageService.saveFilePathForTeacher(memberId, fileUrl);
            return ResponseDto.success("file upload successful", fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseDto.failed("file upload failed", null);
        }
    }

    @GetMapping("/{bucketName}/{key:.+}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable String bucketName, @PathVariable String key) {
        try {
            byte[] fileBytes = s3ImageService.getImageBytes(bucketName, key);
            ByteArrayResource resource = new ByteArrayResource(fileBytes);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", key); // 파일을 첨부 파일로 다운로드 받도록 설정
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
