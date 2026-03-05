package com.example.sweezcustoms.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface SignatureService {
    String uploadSignature(MultipartFile signature);
    byte[] getSignature(String signatureName);

}
