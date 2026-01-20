package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {
    Optional<FileStorage> findByPath(String path);
    Optional<FileStorage> findByOriginalFileName(String originalFileName);
}
