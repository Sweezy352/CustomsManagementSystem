package com.example.sweezcustoms.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:minio.properties")
public class MinIoConfig {
    @Value("${minio.url}")
    private String minioUrl;
    @Value("${minio.username}")
    private String minioUsername;
    @Value("${minio.password}")
    private String minioPassword;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder().credentials(minioUsername, minioPassword).endpoint(minioUrl).build();
    }
}
