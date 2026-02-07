package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.service.MinIoService;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MinIoServiceImpl implements MinIoService {
    private final MinioClient minioClient;

    @Override
    public void upload(String bucketName, MultipartFile multipartFile) {
        try{
            ensureBucketExists(bucketName);
            minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName).stream(multipartFile.getInputStream(), multipartFile.getSize(), -1).contentType(multipartFile.getContentType()).build());
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public InputStream streamFile(String bucketName, String fileName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public String getContentType(String bucketName, String fileName) {
        try{
            StatObjectResponse statObjectResponse = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(fileName).build());
            return statObjectResponse.contentType();
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public boolean fileExists(String bucketName, String fileName) {
        try{
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(fileName).build());
            return true;
        }catch (ErrorResponseException ex){
            if(ex.errorResponse().code().equals("NoSuchKey")){
                return false;
            }else{
                throw new RuntimeException(ex.getMessage());
            }
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void uploadWithBytes(String bucketName, byte[] bytes, String fileName, String contentType) {
        try{
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(new ByteArrayInputStream(bytes), bytes.length, -1).build());
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public byte[] downloadFile(String bucketName, String fileName) {
        try{
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build()).readAllBytes();
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }


    private void ensureBucketExists(String bucketName){
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if(!found){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
}
