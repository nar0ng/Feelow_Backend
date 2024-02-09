package com.feelow.Feelow.S3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class S3ImageService {

    private final AmazonS3Client s3Client;


    private final String bucketName;

    @Autowired
    public S3ImageService(AmazonS3Client s3Client,@Value("${cloud.aws.s3.bucket}")String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    public byte[] getImageBytes(String bucketName, String key) throws IOException {
        S3Object s3Object = s3Client.getObject(bucketName, key);
        return IOUtils.toByteArray(s3Object.getObjectContent());
    }

    public void uploadImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null));
    }
}
