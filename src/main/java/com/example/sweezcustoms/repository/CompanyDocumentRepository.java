package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.CompanyDocumentEntity;
import com.example.sweezcustoms.entity.CompanyEntity;
import com.example.sweezcustoms.enums.CompanyDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Optional;

@Repository
public interface CompanyDocumentRepository extends JpaRepository<CompanyDocumentEntity, Long> {
    Optional<CompanyDocumentEntity> findByFileName(String fileName);
}
