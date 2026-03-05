package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.service.MinIoService;
import com.example.sweezcustoms.service.PhotoProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class PhotoProfileServiceImpl implements PhotoProfileService {
    private final MinIoService minIoService;
    @Value("${bucket.name.photo-profile}")
    private String bucketName;

    @Override
    public String uploadPhoto(MultipartFile multipartFile) {
        try {
            String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            String uniqueFileName = String.format("profile-picture_%d.%s",System.currentTimeMillis(), extension);
            minIoService.uploadWithBytes(bucketName, multipartFile.getBytes(), uniqueFileName, multipartFile.getContentType());
            return uniqueFileName;
        }catch (IOException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public InputStream getPhoto(String photoName) {
        return minIoService.streamFile(bucketName, photoName);
    }
}
