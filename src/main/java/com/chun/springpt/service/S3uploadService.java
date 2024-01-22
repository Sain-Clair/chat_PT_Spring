package com.chun.springpt.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3uploadService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> saveFile(String path, List<MultipartFile> uploadImgs) throws IOException {
        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile multipartFile : uploadImgs) {
            String originalFilename = multipartFile.getOriginalFilename();
            String fullPath = path + originalFilename;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            amazonS3.putObject(bucket, fullPath, multipartFile.getInputStream(), metadata);
            fileUrls.add(amazonS3.getUrl(bucket, fullPath).toString());
        }

        return fileUrls;
    }
}
