package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.service.MinIoService;
import com.example.sweezcustoms.service.SignatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SignatureServiceImpl implements SignatureService {
    private final MinIoService minIoService;
    @Value("${minio.bucket.name.signatures}")
    private String bucketName;

    @Override
    public String uploadSignature(MultipartFile signature) {
        try {
            String uniqueSignatureName = String.format("signature-%d.png", System.currentTimeMillis());
            minIoService.uploadWithBytes(bucketName, signature.getBytes(), uniqueSignatureName, signature.getContentType());
            return uniqueSignatureName;
        }catch (IOException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public byte[] getSignature(String signatureName) {
        return minIoService.downloadFile(bucketName, signatureName);
    }
}
