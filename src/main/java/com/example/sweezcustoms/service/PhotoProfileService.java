package com.example.sweezcustoms.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface PhotoProfileService {
    String uploadPhoto(MultipartFile multipartFile);
    InputStream getPhoto(String photoName);
}
