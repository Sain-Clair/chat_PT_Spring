package com.chun.springpt.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3uploadService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 저장할 경로와, 저장할 파일들을 받아서, 파일들의 url을 반환한다.
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


    public String saveFilewithName(String path, byte[] imageBytes) throws IOException {
        String fileUrls = "";

        if (imageBytes != null && imageBytes.length > 0) {
            InputStream inputStream = new ByteArrayInputStream(imageBytes);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imageBytes.length);
            metadata.setContentType("image/jpeg"); // 이 부분은 필요에 따라 조정 가능

            // S3에 파일 업로드
            amazonS3.putObject(new PutObjectRequest(bucket, path, inputStream, metadata));
            fileUrls = amazonS3.getUrl(bucket, path).toString();
        }

        return fileUrls;
    }

    public void deleteFileFromS3(String filePath) {
// S3 버킷 이름과 파일 경로를 결합하여 전체 경로를 로그로 출력
        String fullFilePath = "s3://" + bucket + "/" + filePath;
        log.info("Attempting to delete file from S3 at full path: {}", fullFilePath);

        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, filePath));
        } catch (Exception e) {
            log.error("Error deleting file from S3: {}", e.getMessage());
            throw e; // 추가적인 예외 처리가 필요할 수 있음
        }
    }
}
