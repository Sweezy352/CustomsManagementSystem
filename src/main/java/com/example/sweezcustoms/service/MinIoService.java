package com.example.sweezcustoms.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinIoService {
    void upload(String bucketName, MultipartFile multipartFile);

    InputStream streamFile(String bucketName, String fileName);

    String getContentType(String bucketName, String fileName);

    boolean fileExists(String bucketName, String fileName);

    void uploadWithBytes(String bucketName, byte[] bytes, String fileName ,String contentType);

    byte[] downloadFile(String bucketName, String fileName);
}
