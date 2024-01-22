package com.chun.springpt.controller;

import com.chun.springpt.service.S3uploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class S3UploadTestController {

    @Autowired
    private S3uploadService s3uploadService;

    @PostMapping("/s3upload")
    public List<String> s3upload(@RequestPart(required = false) List<MultipartFile> uploadImgs) throws IOException {

        return s3uploadService.saveFile("test/", uploadImgs);
    }
}
